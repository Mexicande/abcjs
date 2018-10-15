package cn.com.simpleapp.common;

/**
 * Created by apple on 2018/8/8.
 */

public interface Api {


    String HOST = "http://api.shengcaiquan.com/v1/";

    String CREDIT = HOST + "vip/creditCard";
    String APPLY=HOST+"statistics/log";

    interface Home {
        /**
         * 新品
         **/
        String NEW_PRODUCT = HOST + "vest/onlineProduct";

        /**
         * 滚动
         *
         */

        String PROMOTIONS =HOST+"promotions";
        /**
         * banner
         **/
        String BANNER = HOST + "banners";
        /**
         * 热门
         **/
        String HOT_PRODUCT = HOST + "recommends";
        /**
         * 我要办卡
         */
        String BANK = HOST + "vest/projectLink";
        /**
         * 精品推荐
         */
        String RECOMMEND = HOST + "vest/recommendProduct";

        /**
         * 产品详情
         */
        String PRODUCT_DETAIL = HOST + "vest/detail";

        /**
         * 产品筛选
         */
        String PRODUCT_SCREEN = HOST + "product/list";
        /**
         * 急速
         */
        String TOP_PRODUCT=HOST+"vest/topProduct";
        /**
         * 统计
         */
        String APPLY=HOST+"vest/apply";


        /**
         * 秒杀
         */
        String KILL=HOST+"news";

        /**
         * 详情推荐
         */
        String RELATION=HOST+"vest/relation";




    }

    /**
     * 新or老用户
     **/
    String isOldUser = HOST + "quick/isOldUser";
    /**
     * 验证码获取
     **/
    String CODE = HOST + "sms/register";
    /**
     * 验证码效验
     **/
    String CHECKCODE = HOST + "auth/login";


}
