package zhaoyufeng.bwie.com.shopapp.net;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import zhaoyufeng.bwie.com.shopapp.greendao.DaoMaster;
import zhaoyufeng.bwie.com.shopapp.greendao.DaoSession;
import zhaoyufeng.bwie.com.shopapp.greendao.UserDao;

public class UserDaoData {
    public UserDaoData(){};
    private DaoSession daoSession;
    private static UserDaoData getDao;
    public static UserDaoData getGetDao(){
        if(getDao==null){
            getDao = new UserDaoData();
        }
        return getDao;
    }

    public void init(Context context){
        DaoMaster.DevOpenHelper user = new DaoMaster.DevOpenHelper(context, "user");
        Database db = user.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public void addUser (User user ){
        List<User> users = queryAll();
        for (User u:users) {
            if(u.getType().equals(user.getType())){
                delete(u.getId());
            }
        }
        daoSession.getUserDao().insert(user);
    }
    public  User query (String type){
        User unique = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Type.eq(type)).unique();
        return unique;
    }
    public  List<User> queryAll (){
        List<User> users = daoSession.getUserDao().loadAll();
        return users;
    }
    public void delete(Long id){
        daoSession.getUserDao().deleteByKey(id);
    }
    public void deleteByUser(User user){
        daoSession.getUserDao().delete(user);
    }
    public void addUserList (User user ){
        List<User> users = queryAll();
        for (User u:users) {
            if(u.getData().equals(user.getData())){
                delete(u.getId());
            }
        }
        daoSession.getUserDao().insert(user);
    }
    public  List<User> queryList (String type){
        List<User> list = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Type.eq(type)).orderAsc().list();
        return list;
    }
    public void deleteList(){

    }
}
