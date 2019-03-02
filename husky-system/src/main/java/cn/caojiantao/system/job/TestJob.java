package cn.caojiantao.system.job;

/**
 * @author caojiantao
 */
public class TestJob extends BaseJob {

    @Override
    public void doExecute() {
        System.out.println("=============== start ===============");
        System.out.println(Thread.currentThread().getName() + "is running...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
