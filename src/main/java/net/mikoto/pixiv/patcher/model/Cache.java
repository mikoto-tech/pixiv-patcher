package net.mikoto.pixiv.patcher.model;

import java.util.HashSet;
import java.util.Set;

public abstract class Cache<T> {
    private final int maxTargetCount;
    private final Set<T> targets = new HashSet<>();

    public Cache(int maxTargetCount) {
        this.maxTargetCount = maxTargetCount;
    }

    public boolean isFull() {
        return targets.size() >= maxTargetCount;
    }

    public void addTarget(T target) {
        targets.add(target);
    }

    public Object[] getTargets() {
        return targets.toArray(new Object[0]);
    }

    public void removeAll() {
        targets.clear();
    }

    public boolean isEmpty() {
        return targets.isEmpty();
    }
}
