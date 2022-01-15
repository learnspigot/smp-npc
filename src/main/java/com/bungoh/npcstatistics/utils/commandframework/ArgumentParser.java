package com.bungoh.npcstatistics.utils.commandframework;

/**
 * @author Nucker
 * @project SMP-Core
 * @date 13/01/2022
 */
public interface ArgumentParser<T> {

    T parse(String argument);
}
