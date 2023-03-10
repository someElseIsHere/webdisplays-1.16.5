/*
 * Copyright (C) 2018 BARBOTIN Nicolas
 */

package net.montoyo.wd.data;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import net.montoyo.wd.WebDisplays;
import net.montoyo.wd.net.Messages;
import net.montoyo.wd.net.client.CMessageOpenGui;

import java.util.HashMap;
import java.util.function.Function;

public abstract class GuiData {

    protected static class GuiType {
        Class<?> clazz;
        ResourceLocation id;
    }
    
    private static final HashMap<String, Class<? extends GuiData>> dataTable = new HashMap<>();
    static {
        dataTable.put("SetURL", SetURLData.class);
        dataTable.put("ScreenConfig", ScreenConfigData.class);
        dataTable.put("Keyboard", KeyboardData.class);
        dataTable.put("RedstoneCtrl", RedstoneCtrlData.class);
        dataTable.put("Server", ServerData.class);
    }

    public static Class<? extends GuiData> classOf(String name) {
        return dataTable.get(name);
    }
    
    public GuiData() {
    }
    
//    public GuiData(FriendlyByteBuf buf) {
//        this.deserialize(buf);
//    }
    
    @OnlyIn(Dist.CLIENT)
    public abstract Screen createGui(Screen old, Level world);
    public abstract String getName();

    public void sendTo(ServerPlayer player) {
        Messages.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new CMessageOpenGui(this));
    }

//    public abstract void serialize(FriendlyByteBuf buf);
//    public abstract void deserialize(FriendlyByteBuf buf);
    
}
