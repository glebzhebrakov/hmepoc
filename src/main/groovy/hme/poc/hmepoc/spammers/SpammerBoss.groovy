package hme.poc.hmepoc.spammers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
class SpammerBoss {

    private final AbstractSpammer spammer

    @Autowired
    SpammerBoss(AbstractSpammer spammer) {
        this.spammer = spammer
    }

//    @Scheduled(fixedDelay = 1000l)
    @PostConstruct
    void scheduleSpaming() {
        spammer.spam()
    }
}
