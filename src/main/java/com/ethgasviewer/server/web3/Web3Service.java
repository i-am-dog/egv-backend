package com.ethgasviewer.server.web3;

import com.ethgasviewer.server.properties.Web3Properties;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.ethgasviewer.server.Application.WEB3_PROFILE;

@Service
//@Profile(WEB3_PROFILE)
public class Web3Service {
    public static final DefaultBlockParameter BLOCK_NUMBER = DefaultBlockParameter.valueOf(new BigInteger("11044276"));

    private static final Logger log = LoggerFactory.getLogger(Web3Service.class);
    private Web3j web3;
    private final Set<Disposable> subscriptions = new HashSet<>();
    private final BlockingQueue<Transaction> transactions = new ArrayBlockingQueue<>(10_000);
    private final Web3Properties web3Properties;

    public Web3Service(Web3Properties web3Properties) {
        this.web3Properties = web3Properties;
    }

    @PostConstruct
    public void init() {
        log.info("Connecting to Ethereum ...");
        web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/" + web3Properties.getApiKey()));
        log.info("Successfuly connected to Ethereum");
        subscribe();
    }

    public void subscribe() {
        Disposable subscription = web3.transactionFlowable()
            .subscribe(transactions::put);
        subscriptions.add(subscription);
    }

    public BlockingQueue<Transaction> getTransactions() {
        return transactions;
    }

    @PreDestroy
    private void close() {
        log.info("Close web3");
        subscriptions.forEach(Disposable::dispose);
        web3.shutdown();
    }

}
