package dao;

public class CaminoRepository {
    private static CaminoRepository _INSTANCE;
    private CaminoRepository(){};

    public static CaminoRepository getInstance(){
        if(_INSTANCE==null){
            _INSTANCE= new CaminoRepository();
        }
        return _INSTANCE;
    }
}
