package cn.caojiantao.system.job;

public class TestJob extends BaseJob {

    @Override
    public void doExecute() {
        System.out.println("=============== start ===============");
        System.out.println(Thread.currentThread().getName() + "is running...");
    }
}
