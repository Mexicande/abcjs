package cn.com.simpleapp.net;

/**
 * Created by apple on 2018/5/18.
 */

public interface Api {

     String HOST="http://api.anwenqianbao.com/v2/";


    String HOST1="http://api.shoujiweidai.cn/v1/";

    /**banner **/
    String BANNER1=HOST1+"banner/getBanner";
    /**产品 **/
    String PRODUCT1_LSIT=HOST1+"product/getProduct";

    String APPLY1=HOST1+"product/apply";

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


        /** 新or老用户**/
        String isOldUser1=HOST1+"sms/getCode";
        /** 验证码获取**/
        String CODE1=HOST1+"sms/getcode";
        /** 验证码效验**/
        String CHECKCODE1=HOST1+"quick/login";

    }

    interface  STATUS{
        /** 状态**/
        String getStatus=HOST+"vest/getStatus";
        /**版本更新**/
        String UPDATE=HOST+"vest/version";
    }





}
