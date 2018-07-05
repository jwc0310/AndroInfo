package com.andy.androinfo.java;

/**
 * Created by Administrator on 2018/6/30.
 */

public class ProxyTest {

    public interface Shopping {
        Object[] doShoping(long money);
    }

    public class ShoppingImpl implements Shopping {

        @Override
        public Object[] doShoping(long money) {
            System.out.println("taobao, shangchang, gogogo");
            System.out.println(String.format("lost %s money", money));
            return new Object[] {"shoes", "clothes", "things"};
        }
    }

    public class ProxyShopping implements Shopping {

        private Shopping base;

        public ProxyShopping(Shopping shopping) {
            base = shopping;
        }

        @Override
        public Object[] doShoping(long money) {

            //手续费
            long rate = (long) (money * 0.3);

            long leftShopping = money - rate;
            System.out.println(String.format("花了%s块钱", leftShopping));

            //帮忙买
            Object[] things = base.doShoping(leftShopping);

            //黑操作， 掉包
            if (things != null && things.length > 1)
                things[0] = "次品货";

            return things;
        }
    }


    public static void main(String[] args) {

    }

}
