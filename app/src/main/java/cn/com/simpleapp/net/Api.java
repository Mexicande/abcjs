package cn.com.simpleapp.net;

/**
 * Created by apple on 2018/5/18.
 */

public interface Api {

     String HOST="http://api.anwenqianbao.com/v2/";




    /**banner **/
    String BANNER=HOST+"vest/banner";
    String APPLY=HOST+"vest/apply";

    /**产品 **/
     String PRODUCT_LSIT=HOST+"vest/hotProduct";
    /**iv_welfare_select **/
    String WELFARE=HOST+"vest/welfare";
    /**Credit **/
    String CREDIT = HOST + "vip/creditCard";
    /**今日推荐**/
    String RECOMMEND =HOST+"vest/recommendProduct";
    /**大全**/
    String LSIT=HOST+"vest/product";
    /**帮你借**/
    String HELP=HOST+"borrow/url";
    /**筛选**/
    String SCREEN=HOST+"vest/screening";

    String Notice=HOST+"vest/news";


    interface   LOGIN{
        /** 新or老用户**/
        String  isOldUser=HOST+"quick/isOldUser";
        /** 验证码获取**/
        String  CODE=HOST+"sms/getcode";
        /** 验证码效验**/
        String  CHECKCODE=HOST+"sms/checkCode";
        /** 登陆**/
        String  QUICKLOGIN=HOST+"quick/login";
        /** 完善信息**/
        String IDENTITY =HOST+"quick/addBasicIdentity";


    }

    interface  STATUS{
        /** 状态**/
        String getStatus=HOST+"vest/getStatus";
        /**版本更新**/
        String UPDATE=HOST+"vest/version";
    }





}
