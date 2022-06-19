package de.mzte.quickreplant

import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.block.NetherWartBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldView

fun BlockState.isMaturePlant(): Boolean {
    return when (val block = this.block) {
        is CropBlock -> block.isMature(this)
        is NetherWartBlock -> {
            println(this.get(NetherWartBlock.AGE))
            this.get(NetherWartBlock.AGE) >= 3
        }
        else -> false
    }
}

fun Item.isFittingPlantItem(
    world: WorldView,
    clickedPos: BlockPos,
): Boolean {
    if (this is BlockItem) {
        val block = this.block
        if (block !is CropBlock && block !is NetherWartBlock) return false
        return block.canPlaceAt(world.getBlockState(clickedPos.down()), world, clickedPos)
    }

    return false
}
