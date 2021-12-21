package de.mzte.quickreplant

import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.block.NetherWartBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Item

fun BlockState.isMaturePlant(): Boolean {
    val block = this.block

    return when (block) {
        is CropBlock -> block.isMature(this)
        is NetherWartBlock -> {
            println(this.get(NetherWartBlock.AGE))
            this.get(NetherWartBlock.AGE) >= 3
        }
        else -> false
    }
}

fun Item.isPlantItem(): Boolean {
    if (this is BlockItem) {
        val block = this.block

        return block is CropBlock || block is NetherWartBlock
    }

    return false
}
