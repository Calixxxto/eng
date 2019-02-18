package account.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;


@Service
public class BillingSessionService {

    private AtomicBoolean inProgress = new AtomicBoolean(false);

    public boolean isBillingInProgress() {
        return inProgress.get();
    }
}
