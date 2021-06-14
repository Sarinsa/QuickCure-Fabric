package com.sarinsa.quickcure.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieVillagerEntity.class)
public abstract class ZombieVillagerEntityMixin extends ZombieEntity implements VillagerDataContainer {

	public ZombieVillagerEntityMixin(EntityType<? extends ZombieEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	protected abstract void finishConversion(ServerWorld world);

	@Inject(method = "tick", at = @At("HEAD"))
	private void onTick(CallbackInfo info) {
		ZombieVillagerEntity zombieVillager = (ZombieVillagerEntity) (Object) this;

		if (zombieVillager.isAlive() && zombieVillager.isConverting()) {
			if (!zombieVillager.getEntityWorld().isClient) {
				this.finishConversion((ServerWorld) this.world);
			}
		}
	}
}
