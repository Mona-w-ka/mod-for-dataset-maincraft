package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import java.io.*;

public class RandomTP implements ModInitializer {
    private int tickCounter = 0;

    @Override
    public void onInitialize() {
        System.out.println("Мод запущен");
        ServerTickEvents.END_SERVER_TICK.register(this::onServerTick);
    }

    private void onServerTick(MinecraftServer server) {
        tickCounter++;
        if (tickCounter >= 60) {
            tickCounter = 0;
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                teleportRandomly(player);
            }
        }

    }
    private void teleportRandomly(ServerPlayerEntity player) {
        ServerWorld serverWorld = (ServerWorld) player.getWorld();

        double originX = player.getX();
        double originZ = player.getZ();

        double dx = (Math.random() * 200) - 100;
        double dz = (Math.random() * 200) - 100;

        double newX = originX + dx;
        double newZ = originZ + dz;

        int y = serverWorld.getTopY(Heightmap.Type.WORLD_SURFACE, (int)newX, (int)newZ);

        float yaw = (float)(Math.random() * 360);
        float pitch = (float)(Math.random() * 10 - 0);

        BlockPos feetPos = BlockPos.ofFloored(newX, y - 1, newZ);
        BlockState blockState = serverWorld.getBlockState(feetPos);
        Block block = blockState.getBlock();

        int attempts = 0;
        final int MAX_ATTEMPTS = 10;

        if (block == Blocks.WATER){

            while(block == Blocks.WATER && attempts < MAX_ATTEMPTS) {

                originX = player.getX();
                originZ = player.getZ();

                dx = (Math.random() * 200) - 100;
                dz = (Math.random() * 200) - 100;

                newX = originX + dx;
                newZ = originZ + dz;

                feetPos = BlockPos.ofFloored(newX, y - 1, newZ);
                block = serverWorld.getBlockState(feetPos).getBlock();
            }

            if (attempts >= MAX_ATTEMPTS){
                player.teleport(serverWorld, newX, y, newZ, yaw, pitch);
                try {
                    FileWriter writer = new FileWriter("tplog.txt", true);
                    writer.write("teleport");
                    writer.close();
                }
                catch (IOException e){

                    System.out.println("Произошла ошибка при записи в файл:");

                }
            }

        }

        else {
            player.teleport(serverWorld, newX, y, newZ, yaw, pitch);
            try {
                FileWriter writer = new FileWriter("tplog.txt", true);
                writer.write("teleport");
                writer.close();
            }
            catch (IOException e){

                System.out.println("Произошла ошибка при записи в файл:");

            }

        }
    }
}




