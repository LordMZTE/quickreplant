package de.mzte.quickreplant

import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.block.CropBlock
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

@Suppress("unused")
fun init() {
    UseBlockCallback.EVENT.register(::onBlockUse)
}

fun onBlockUse(player: PlayerEntity, world: World, hand: Hand, hitResult: BlockHitResult): ActionResult {
    if (player !is ClientPlayerEntity || world !is ClientWorld)
        return ActionResult.PASS

    val handItem = player.getStackInHand(hand).item
    if (handItem.isPlantItem()) {
        val clickedPos = hitResult.blockPos
        val state = world.getBlockState(clickedPos)
        if (state.isMaturePlant()) {
            MinecraftClient.getInstance().interactionManager?.let { interactionManager ->
                interactionManager.attackBlock(clickedPos, Direction.DOWN)
                // So at this point, you may be asking yourself "how does this replant the crop?" and the truth is,
                // I have absolutely no clue. All I know is that this works and it seriously freaks me out why it does.
                return ActionResult.SUCCESS
            }
        }
    }
    return ActionResult.PASS
}
