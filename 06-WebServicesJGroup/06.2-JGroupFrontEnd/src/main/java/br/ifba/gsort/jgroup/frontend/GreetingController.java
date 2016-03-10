package br.ifba.gsort.jgroup.frontend;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.jgroups.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ifba.gsort.jgroup.JCluster;

@Controller
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    JCluster jCluster;

    @RequestMapping("/greeting")
    public @ResponseBody Greeting greeting(@RequestParam(required=false, defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping("/instances/size")
    public @ResponseBody int getAllInstancesSize() {
        return jCluster.getChannel().getView().getMembers().size();
    }
    @RequestMapping("/instances")
    public @ResponseBody List<Address> getAllInstances() {
        return jCluster.getChannel().
        		getView().
        		getMembers();

    }
}
