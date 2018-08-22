package thread.future;

public class Res {

    public Long id;
    public String key;

    public Res(Long id,String key){
        this.id = id;
        this.key = key;
    }


    @Override
    public String toString() {
        return "Res{" +
                "id=" + id +
                ", key='" + key + '\'' +
                '}';
    }
}
