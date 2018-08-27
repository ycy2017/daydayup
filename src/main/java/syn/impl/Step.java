package syn.impl;

import syn.AbstractScript;
import syn.dao;
import syn.data.LocalEntity;
import syn.data.PeerEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 增量同步
 *
 *  (1)适用于数据量很大的数据
 *  (2)自增主键
 *
 *
 *
 *
 * 不可以用分页的方式,如果对方数据新增和删除平凡,分页会导致数据
 */
public class Step extends AbstractScript {

    String peersql = "  *** from table where  id>?  order by id asc limited 0,?";

    String localSql = " *** where table id>?  order by id asc limited 0,? ";


    public void getdata(){
        //业务方全量数据
        List<PeerEntity> Peerlist = new dao().getPeerList();
        List<LocalEntity> Locallist = new dao().getLocalList();
        compare(Peerlist,Locallist);
    }

    private void compare(List<PeerEntity> peerlist, List<LocalEntity> locallist) {

//        while






        Map<Long,Object> localMap = new HashMap<Long,Object>();
        for (LocalEntity LocalEntity:locallist){
            localMap.put(LocalEntity.localId,new Object());
        }
        //
        List<LocalEntity> insertlist = new LinkedList<>();
        List<LocalEntity> updatelist = new LinkedList<>();
        List<Long> deletelist = new LinkedList<>();
        for (PeerEntity peerEntity:peerlist){
            Long peerId = peerEntity.peerId;
            LocalEntity localentity = new LocalEntity();
            //处理逻辑的地方
            localentity.fillBean(peerEntity);

            if (localMap.get(peerId)!=null){
                //移除
                updatelist.add(localentity);
            }else{
                //插入
                insertlist.add(localentity);
            }
            localMap.remove(peerId);
        }

        for(Long id : localMap.keySet()){
            //移除
            deletelist.add(id);
        }



    }

    /**
     * 新增和修改
     */
    public void dealData(){



    }

    /**
     * 逻辑删除 或者 物理删除
     */
    public void deleteData(){



    }




}
