package net.mikoto.pixiv.patcher.model;

import java.util.HashSet;
import java.util.List;
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

    public List<T> getTargets() {
        return targets.stream().toList();
    }

    public void removeAll() {
        targets.clear();
    }

    public boolean isEmpty() {
        return targets.isEmpty();
    }
}
