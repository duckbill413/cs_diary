package design_pattern;

import java.util.ArrayList;
import java.util.List;

interface Subject {
    void register(Observer obj);
    void unregister(Observer obj);
    void notifyObservers();
    Object getUpdate(Observer obj);
}

interface Observer {
    void update();
}

class Topic implements Subject {
    private List<Observer> observers;
    private String message;

    public Topic() {
        this.observers = new ArrayList<>();
        this.message = "";
    }

    @Override
    public void register(Observer obj) {
        if (!observers.contains(obj)) observers.add(obj);
    }

    @Override
    public void unregister(Observer obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyObservers() {
        this.observers.forEach(Observer::update);
    }

    @Override
    public Object getUpdate(Observer obj) {
        return this.message;
    }

    public void postMessage(String message){
        System.out.println("Message sended to Topic: " + message);
        this.message = message;
        notifyObservers();
    }

    public String getMessage() {
        return message;
    }
}

class TopicSubscriber implements Observer{
    private String name;
    private Subject topic;

    public TopicSubscriber(String name, Subject topic){
        this.name = name;
        this.topic = topic;
    }

    @Override
    public void update() {
        String msg = (String) topic.getUpdate(this);
        System.out.println(name + ":: got message >> " + msg);
    }
}
public class Observer_Pattern {
    public static void main(String[] args) {
        Topic topic = new Topic();

        Observer a = new TopicSubscriber("A", topic);
        Observer b = new TopicSubscriber("B", topic);
        Observer c = new TopicSubscriber("C", topic);

        topic.register(a);
        topic.register(b);
        topic.register(c);

        topic.postMessage("Let's learn Observer Pattern");
        System.out.println("Topic Message: " + topic.getMessage());
    }
}
