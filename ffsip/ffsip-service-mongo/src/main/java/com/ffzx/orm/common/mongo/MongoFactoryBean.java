package com.ffzx.orm.common.mongo;

import com.mongodb.*;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/17.
 */
public class MongoFactoryBean extends AbstractFactoryBean<MongoClient> {

    /**
     * example：  root:123456@192.168.2.195:27017
     * 表示服务器列表
     */
    private String[] serverStrings;

    /**
     * mongo配置对象
     */
    private MongoClientOptions mongoOptions;

    /**
     * 是否主从分离，默认为false
     */
    private boolean readSecondary = false;
    /**
     * 设定写策略，默认采用SAFE模式(需要抛异常)
     */
    private WriteConcern writeConcern = WriteConcern.SAFE;

    public String[] getServerStrings() {
        return serverStrings;
    }

    public void setServerStrings(String[] serverStrings) {
        this.serverStrings = serverStrings;
    }

    public MongoClientOptions getMongoOptions() {
        return mongoOptions;
    }

    public void setMongoOptions(MongoClientOptions mongoOptions) {
        this.mongoOptions = mongoOptions;
    }

    public WriteConcern getWriteConcern() {
        return writeConcern;
    }

    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    public boolean isReadSecondary() {
        return readSecondary;
    }

    public void setReadSecondary(boolean readSecondary) {
        this.readSecondary = readSecondary;
    }

    @Override
    public Class<?> getObjectType() {
        return MongoClient.class;
    }

    @Override
    protected MongoClient createInstance() throws Exception {
        MongoClient mongo = createClient(getServiceInfos().get(0));

        //设定主从分离  
        if (readSecondary) {
            mongo.setReadPreference(ReadPreference.secondaryPreferred());
        }
        //设定写策略  
        mongo.setWriteConcern(writeConcern);
        return mongo;
    }

    /**
     * 初始化mongo
     *
     * @return
     * @throws Exception
     */
    private MongoClient initMongo() throws Exception {
        //根据条件创建mongo实例  
        MongoClient mongo = null;
        List<ServerAddress> serverList = getServerList();
        mongo = new MongoClient(serverList.get(0), getCredentials("", ""));
        return mongo;
    }

    private MongoClient createClient(Map<String, String> paras) {
        MongoClient client = null;
        if (paras.size() == 2) {
            ServerAddress address = getServerAddress(paras.get("host"), paras.get("port"));
            client = new MongoClient(address);
        } else if (paras.size() == 4) {
            ServerAddress address = getServerAddress(paras.get("host"), paras.get("port"));
            List<MongoCredential> mongoCredentials = getCredentials(paras.get("userName"), paras.get("password"));
            client = new MongoClient(address, mongoCredentials);
        } else {
            throw new RuntimeException("链接参数错误：" + paras);
        }
        return client;
    }

    private List<Map<String, String>> getServiceInfos() {
        List<Map<String, String>> serverInfos = new ArrayList<>();
        for (String u : serverStrings) {
            String[] _t = u.split("@");
            Map<String, String> info = new HashMap<>();
            if (_t.length == 1) {
                String[] t2 = _t[0].split(":");
                info.put("host", t2[0]);
                info.put("port", t2[1]);
            } else if (_t.length == 2) {
                String[] t2 = _t[0].split(":");
                String[] t3 = _t[1].split(":");
                info.put("userName", t2[0]);
                info.put("password", t2[1]);
                info.put("host", t3[0]);
                info.put("port", t3[1]);
            } else {
                throw new RuntimeException("链接字符串格式错误,应该为：root:123456@192.168.2.195:27017 或者 192.168.2.195:27017 之一");
            }
            serverInfos.add(info);
        }
        return serverInfos;
    }

    /**
     * 获取mongo地址
     *
     * @return
     */
    private List<ServerAddress> getServerList() throws Exception {
        List<ServerAddress> serverList = new ArrayList<>();
        try {
            for (String serverString : serverStrings) {
                String[] temp = serverString.split(":");
                String host = temp[0];
                if (temp.length > 2) {
                    throw new IllegalArgumentException(
                            "Invalid server address string:" + serverString
                    );
                }
                if (temp.length == 2) {
                    serverList.add(new ServerAddress(host, Integer.parseInt(temp[1])));
                } else {
                    serverList.add(new ServerAddress(host));
                }
            }
            return serverList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(
                    "Error while converting serverString to ServerAddressList", e
            );
        }
    }

    private ServerAddress getServerAddress(String host, String port) {
        return new ServerAddress(host, Integer.parseInt(port));
    }

    private List<MongoCredential> getCredentials(String userName, String password) {
        List<MongoCredential> mongoCredentials = new ArrayList<>();
        MongoCredential credential = MongoCredential.createCredential(userName, "admin", password.toCharArray());
        mongoCredentials.add(credential);
        return mongoCredentials;
    }
}  