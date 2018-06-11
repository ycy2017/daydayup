package com.mas.dtc.scripts.outsource.fileprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.mas.dtc.scripts.outsource.fileprocess.impl.ProcessCsv;
import com.mas.dtc.scripts.outsource.fileprocess.impl.ProcessExcel;
import com.mas.dtc.scripts.outsource.fileprocess.impl.ProcessJson;
import com.mas.dtc.scripts.outsource.source.IDataSource;
import com.mas.dtc.scripts.outsource.task.FutureData;
import com.mas.dtc.scripts.outsource.task.ReadTask;
import com.mas.dtc.scripts.outsource.task.WriteTask;

/**
 * 
 * Process基础类,匹配接口标签数据
 * (注意:TD的白天夜晚常活动区域服务,默认为其匹配百度地图数据)
 * @author Mathsys
 *
 */
public abstract class BaseFileProcess {

	/**
	 * 存放参数
	 */
	protected List<String> ids = new LinkedList<String>();

	/**
	 * 存放参数
	 */
	protected int index;

	/**
	 * 存放标签
	 */
	protected List<String> tagHead = new LinkedList<String>();

	/**
	 * 日志
	 */
	protected static final Logger LOG = Logger.getLogger(BaseFileProcess.class);

	/**
	 * 数据来源
	 */
	protected IDataSource dataSource; 
	
	/**
	 * 分割符
	 */
	public static final String SPLIT = "_";
	
	/**
	 * 分割符
	 */
	public static final String HEADSPLIT = "|";
	
	
	/**
	 * 输出路径
	 */
	protected String outFilePath;
	
	/**
	 * 请求参数个数
	 * 
	 */
	protected int idsAccount;
	
	
	//访问外部的多线程调用
	public ExecutorService rs  ;
	
	//访问队列
	BlockingQueue<Runnable> workQueue ;
	
	final int MAX_WORK_QUEUE = 100;
	
	//写线程
	public ExecutorService ws  ;
	
	//写队列
	BlockingQueue<Runnable> writeWorkQueue ;

	final int MAX_Write_WORK_QUEUE = 100;
	
	/**
	 * 初始化
	 */
	public BaseFileProcess(){
		
		//初始化队列
		this.workQueue=new LinkedBlockingQueue<Runnable>();
		//cpu核心数
		int cpuThread = Runtime.getRuntime().availableProcessors();
		//访问线程
		
		this.rs = new ThreadPoolExecutor(cpuThread, cpuThread,
                0L, TimeUnit.MILLISECONDS,
                this.workQueue );
	    
		//写队列
		this.writeWorkQueue = new LinkedBlockingDeque<Runnable>();
		//写线程,必需只能是一个线程的线程池,要保证数据写入的顺序
		this.ws = new ThreadPoolExecutor(1, 1,
		                0L, TimeUnit.MILLISECONDS,
		                this.writeWorkQueue );
		
	}
	
	/**
	 * 获取BaseProcess的实例
	 * @param command 命令行
	 * @return BaseProcess 的实例
	 */
	public static BaseFileProcess getProcess(String[] command,IDataSource bds) {
	 
		LOG.info("command : " + Arrays.toString(command));
		//检查参数的个数
		if (command == null || (command.length != 3 )) {
			LOG.error("command error , should input \"filepath\" ,\"sourceMeta\", \"type\"");
			return null;
		}
		
		String inFilePath = command[0];
		boolean flag = true;
		//检查路径是否存在并且是否是文件
		File file = new File(inFilePath);
		if (!file.exists() || !file.isFile()) {
			LOG.error("not find file from " + inFilePath+ " or " + inFilePath + " is not file");
			flag = false;
		}
		
		if (!flag) {
			return null;
		}
		
		BaseFileProcess process = null;
		if (inFilePath.endsWith(".csv")) {
			process = new ProcessCsv(bds);
		} else if (inFilePath.endsWith(".xlsx")) {
			process = new ProcessExcel(bds);
		}else if (inFilePath.endsWith(".txt")){
			process = new ProcessJson(bds);
		}
		
		return process;
	}

	/**
	 * 检查(sourcemeta位)和(type位)执行命令行
	 * 
	 * @param command 命令行
	 */
	public void doRun(String[] command) {

		String inFilePath = command[0];
		String sourceMetaStr = command[1];
		String type = command[2];
		//检查参数是否异常(参数是否符合枚举)
		
		if (!checkCommand( type )||!setDataSourceField(sourceMetaStr)) {
			LOG.error("");
			return ;
		}
		
		//初始化表头
		initTableHead(inFilePath);
		
		//执行
		doRunwithCommand(inFilePath, type);
		LOG.info(this.toString());
	}

	/**
	 * 根据设置sourceMeta位,设置DataSource的成员属性
	 * @param sourceMetaStr 唯一标识
	 * @return boolean
	 */
	public boolean setDataSourceField(String sourceMetaStr) {
		boolean flag =this.dataSource.setDataSourceField( sourceMetaStr);
		return flag;
	}

	/**
	 * 
	 * 查询和导出数据
	 * 
	 * @param inFilePath 模版路径
	 * @param type origin:调用原始层数据,mapping:调用dlc转化后的数据
	 */
	public void doRunwithCommand(String inFilePath, String type) {
		// 目标文件路径名称
		String outFilePath = createOutFilePath(inFilePath);
		this.outFilePath = outFilePath;
		// 导出数据
		getDataToExport(inFilePath, outFilePath, type);
	}

	/**
	 * 
	 * 按照参数/需求标签/index 读取表头信息
	 * 
	 * @param inFilePath 文件路径
	 */
	public abstract void initTableHead(String inFilePath);

	/**
	 * 导出excel
	 * 
	 * @param inFilePath 模版路径
	 * @param outFilePath 文本导出路径
	 * @param type origin:调用原始层数据,mapping:调用dlc转化后的数据
	 */
	public abstract void getDataToExport(String inFilePath, String outFilePath, String type);

	/**
	 * 
	 * 调用接口
	 * 
	 * @param ids 调用接口的入参
	 * @param type origin:调用原始层数据,mapping:调用dlc转化后的数据
	 * @param other
	 * @return 接口返回的数据
	 */
	public Map<String, Object> doFetechDataAndWrite(Map<String, Object> ids, String type,Object ... other) {
		
		//访问数据可以做成线程并发的,这里可以做成线程并发
		while(true){
			//队列达到MAX_WORK_QUEUE,就要死循环等着,为了防止无限大的队列,导致开销太大,程序挂掉
			if(this.workQueue.size() <  this.MAX_WORK_QUEUE){
				
				//调用外部线程池,获取"未来结果"
				Future<FutureData> future= this.rs.submit(new ReadTask(this.dataSource, ids, type,other));
				
				while(true){
					if(this.writeWorkQueue.size() < this.MAX_Write_WORK_QUEUE){
						//把"未来结果"交给写入线程
						this.ws.execute(new WriteTask(this, future));
						break;
					}else{
						try {
							LOG.warn(" writeWorkQueue size get Max , wait 1 seconds...");
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							LOG.error(e.getMessage());
						}
					}
					
				}
				
				//"未来结果"放到写入线程就退出
				break;
			}else{
				try {
					LOG.warn(" workQueue size get Max , wait 1 seconds...");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					LOG.error(e.getMessage());
				}
			}
			
		}
		
		return null;
	}

	/**
	 * 并发的方法,如何写数据, 交给子类取实现
	 * @param ids
	 * @param srcMap
	 * @param objects 
	 */
	public abstract void doWriteToFile(Map<String, Object> ids, Map<String, Object> srcMap, Object [] objects);
	
	
	/**
	 * 关闭资源
	 */
	public void closeSource(){
		this.rs.shutdown();
		this.ws.shutdown();
		LOG.info(" threadpoolexecutor is shutdown ");
		while(true){
			
			if(this.ws.isTerminated() && this.rs.isTerminated()){
				//关闭6️流,交给子类取实现
				closeIO();
				
				break;
			}else {
				try {
					LOG.warn("threadpoolexecutor shutdown is failure,because threadpoolexecutor is running  , wait 3 seconds...");
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					LOG.error(e.getMessage());
				}

			}
		}
	};
	
	/**
	 * 关闭
	 */
	public abstract void closeIO();
	
	/**
	 * 检查命令行
	 * 
	 * @param sourceMetaStr 接口唯一标识
	 * @param type type origin:调用原始层数据,mapping:调用dlc转化后的数据
	 * @return boolean
	 */
	public boolean checkCommand(String type) {
		boolean flag = this.dataSource.checkCommand(type);
		return flag;
	}

	/**
	 * 生成新的文本路径
	 * 
	 * @param inFilePath 模版路径
	 * @return String 生成的文件路径
	 */
	public String createOutFilePath(String inFilePath) {
		String absolutelyInFilePath = null;
		//获取该文件的绝对路径
		File file = new File(inFilePath);
		if(file.exists() && file.isFile()){
			absolutelyInFilePath = file.getAbsolutePath();
			int index = absolutelyInFilePath.lastIndexOf(".");
			String beforeFilePaht = absolutelyInFilePath.substring(0, index);
			String afterFilePaht = absolutelyInFilePath.substring(index, absolutelyInFilePath.length());
			String OutFilePath = beforeFilePaht + "_" + new Date().getTime() + afterFilePaht;
			return OutFilePath;
		}
		return null;
	}
	
	/**
	 * 根据标签层次,找出对应的值
	 * 
	 * @param map  如:{"data":{"RSL":[{"RS":1},{"RS":2}]}}
	 * @param mapPath key路径 比如: "data.RSL.1.RS"
	 * @return "1"
	 */
	public Object getValueByKey(Map<String, Object> map, String mapPath) {
		Object value = map.get(mapPath);
		return value;
	}

	/**
	 * 
	 * 为字符串每个一定长度添加":"
	 * 
	 * @param stringInfo 
	 * @param n 每隔多少位添加一个":"
	 * @return String 
	 */
	public static String getMacString(String stringInfo, int n) {
		StringBuilder macSB = new StringBuilder(stringInfo);
		for (int i = n; i < macSB.length(); i += n + 1) {
			macSB.insert(i, ":");
		}
		return macSB.toString();
	}
	
	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<String> getTagHead() {
		return tagHead;
	}

	public void setTagHead(List<String> tagHead) {
		this.tagHead = tagHead;
	}

	/**
	 * 获取表头,包含"|"
	 * 
	 * @return ["mac","imei","|"]
	 */
	public List<String> getHeaders() {
		List<String> headers = new ArrayList<String>();
		headers.addAll(this.ids);
		headers.addAll(this.tagHead);
		return headers;
	}

	
	@Override
	public String toString() {
		return "BaseProcess [ids=" + ids + ", index=" + index + ", tagHead=" + tagHead + ", dataSource=" + dataSource
				+ "]";
	}


	
}
