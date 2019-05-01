package Engine.src.Controller;

import Engine.src.Components.*;
import Engine.src.ECS.Pair;
import Engine.src.Triggers.Events.Event;
import Engine.src.Triggers.Events.ObjectEvents.Deflect;
import Engine.src.Triggers.Events.ObjectEvents.Portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultGame {
    private static final String BLOCK_IMAGE = "/img/block.jpg";
    private static final String USER_IMAGE = "/img/mario.png";
    private static final String PORTAL_IMAGE = "/img/portal.jpg";
    private static final double USER_WIDTH = 30;
    private static final double USER_HEIGHT = 50;
    private static final double USER_MOVEMENT_VELOCITY = 10;
    private static final double USER_JUMP_VELOCITY = 10; //good to increase if you increase gravity and vice versa
    private static final double GRAVITY = 1;

    private Map<Integer, Map<Class<? extends Component>, Component>> myActiveObjects;
    private Map<Pair<String>, Pair<List<Event>>> myCollisionMap;
    private List<Map<Class<? extends Component>, Component>> myBlocks;

    public DefaultGame() {
        myActiveObjects = new HashMap<>();
        myCollisionMap = new HashMap<>();
        addActiveObjects();
        addCollisions();
    }

    private void addActiveObjects() {
        makeUser();

        myBlocks = new ArrayList<>();

        addBlock(0, 200, 200);
        includeTrampoline(300, 400, 100);
        addBlock(550, 200, 200);

        includeShooter(1500, 300, 100, true);

        addBlock(2000, 200, 50); //make these stop your x velocity and end the game
        addBlock(2200, 400, 50);
        addBlock(2400, 100, 50);
        addBlock(2600, 200, 50);
        addBlock(2800, 400, 50);
        addBlock(3000, 0, 50);
        addBlock(3200, 100, 50);
        addBlock(3400, 500, 50);
        addBlock(3600, 100, 50);

        includeShooter(4200, 300, 100, false);

        addImpassableAndTags();

        int blockNumber = 4;
        for (var block : myBlocks) {
            myActiveObjects.put(blockNumber, block);
            blockNumber++;
        }
    }

    private void includeTrampoline(double x, double y, double width) {
        Map<Class<? extends Component>, Component> trampoline = new HashMap<>();
        trampoline.put(BasicComponent.class, new BasicComponent(BLOCK_IMAGE, x, y,width, 75));
        List<String> trampolineTag = new ArrayList<>();
        trampolineTag.add("TRAMPOLINE");
        trampoline.put(TagsComponent.class, new TagsComponent(trampolineTag));
        myActiveObjects.put(1, trampoline);
    }

    private void includeShooter(double x, double y, double width, boolean shoot) {
        Map<Class<? extends Component>, Component> trampoline = new HashMap<>();
        trampoline.put(BasicComponent.class, new BasicComponent(PORTAL_IMAGE, x, y,width, 75));
        List<String> trampolineTag = new ArrayList<>();
        if (shoot)
            trampolineTag.add("SHOOTER");
        else
            trampolineTag.add("STOPPER");
        trampoline.put(TagsComponent.class, new TagsComponent(trampolineTag));

        int id = shoot ? 2 : 3;
        myActiveObjects.put(id, trampoline);
    }

    private void addBlock(double x, double y, double width) {
        Map<Class<? extends Component>, Component> block = new HashMap<>();
        myBlocks.add(block);
        block.put(BasicComponent.class, new BasicComponent(BLOCK_IMAGE, x, y,width, 75));
    }

    private List<String> addImpassableAndTags() {
        List<String> blockTag = new ArrayList<>();
        blockTag.add("BLOCK");
        for (var block : myBlocks) {
            block.put(ImpassableComponent.class, new ImpassableComponent(true));
            block.put(TagsComponent.class, new TagsComponent(blockTag));
        }
        return blockTag;
    }

    private void makeUser() {
        Map<Class<? extends Component>, Component> user = new HashMap<>();
        user.put(BasicComponent.class, new BasicComponent(USER_IMAGE, 50, 50, USER_WIDTH, USER_HEIGHT));
        user.put(MotionComponent.class, new MotionComponent(0, 0, 0, GRAVITY, 0, USER_MOVEMENT_VELOCITY, 0));
        user.put(JumpComponent.class, new JumpComponent(USER_JUMP_VELOCITY));
        List<String> userTag = new ArrayList<>();
        userTag.add("USER");
        user.put(TagsComponent.class, new TagsComponent(userTag));
        myActiveObjects.put(0, user);
    }

    private void addCollisions() {
        //makeCollision("USER", "BLOCK", new Deflect(new ArrayList<>(), 0));
        makeCollision("USER", "TRAMPOLINE", new Deflect(new ArrayList<>(), 0));
        makeCollision("USER", "SHOOTER", new Portal(new ArrayList<>(), true));
        makeCollision("USER", "STOPPER", new Portal(new ArrayList<>(), false));
    }

    private void makeCollision(String tag1, String tag2, Event event) {
        var tagPair = new Pair<>(tag1, tag2);
        var list1 = new ArrayList<Event>();
        var list2 = new ArrayList<Event>();
        list1.add(event);
        myCollisionMap.put(tagPair, new Pair<>(list1, list2));
    }

    public Map<Integer, Map<Class<? extends Component>, Component>> getActiveObjects() {
        return myActiveObjects;
    }

    public Map<Pair<String>, Pair<List<Event>>> getCollisionMap() {
        return myCollisionMap;
    }
}
