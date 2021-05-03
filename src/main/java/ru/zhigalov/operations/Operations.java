package ru.zhigalov.operations;

import java.util.ArrayList;
import java.util.List;

public class Operations {
    public List<Integer> createListFromOneToN(int n) {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            result.add(i);
        }
        return result;
    }

    public long getSumOfList(List<Integer> input) {
        long sum = 0;
        for (int i : input) {
            sum = sum + i;
        }
        return sum;
    }
}
