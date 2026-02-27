package com.farcr.nomansland.common.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.AddSectionGeometryEvent;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import java.util.List;

@Debug(export = true)
@Mixin(SectionCompiler.class)
public class SectionCompilerMixin {
    @Final
    @Shadow
    private BlockRenderDispatcher blockRenderer;

    @Inject(method = "compile(Lnet/minecraft/core/SectionPos;Lnet/minecraft/client/renderer/chunk/RenderChunkRegion;Lcom/mojang/blaze3d/vertex/VertexSorting;Lnet/minecraft/client/renderer/SectionBufferBuilderPack;Ljava/util/List;)Lnet/minecraft/client/renderer/chunk/SectionCompiler$Results;", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"))
    private void addSnowLayer(SectionPos sectionPos, RenderChunkRegion region, VertexSorting vertexSorting, SectionBufferBuilderPack sectionBufferBuilderPack, List<AddSectionGeometryEvent.AdditionalSectionRenderer> additionalRenderers, CallbackInfoReturnable<SectionCompiler.Results> cir, @Local PoseStack posestack, @Local(ordinal = 2) BlockPos blockpos2, @Local BufferBuilder bufferbuilder1, @Local BlockState blockstate, @Local RandomSource randomsource) {
        BakedModel snowModel = blockRenderer.getBlockModel(Blocks.SNOW.defaultBlockState());
        ModelData modelData = snowModel.getModelData(region, blockpos2, Blocks.SNOW.defaultBlockState(), region.getModelData(blockpos2));
        posestack.pushPose();
        posestack.translate(
                (float)SectionPos.sectionRelative(blockpos2.getX()),
                (float)SectionPos.sectionRelative(blockpos2.getY()),
                (float)SectionPos.sectionRelative(blockpos2.getZ())
        );
        this.blockRenderer.renderBatched(blockstate, blockpos2, region, posestack, bufferbuilder1, true, randomsource, modelData, RenderType.CUTOUT_MIPPED);
        posestack.popPose();
    }
}
