package softuni.springproject.scheduledJobs.implementation;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import softuni.springproject.scheduledJobs.ScheduledJob;
import softuni.springproject.services.services.ChefsService;

@Component
public class RepUpChefsSchedulerJob implements ScheduledJob {
    private final ChefsService chefsService;

    public RepUpChefsSchedulerJob(ChefsService chefsService) {
        this.chefsService = chefsService;
    }

    @Override
    @Scheduled(cron =  "0 0/1 * 1/1 * ?")
    public void scheduledJob() {
        chefsService.repUpChefs();
    }
}
