package cn.caojiantao.husky.system.job;

/**
 * @author caojiantao
 */
public class TestJob extends BaseJob {

    @Override
    public String doExecute() {
        System.out.println("=============== start ===============");
        System.out.println(Thread.currentThread().getName() + "is running...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "test job 执行成功";
    }
}
