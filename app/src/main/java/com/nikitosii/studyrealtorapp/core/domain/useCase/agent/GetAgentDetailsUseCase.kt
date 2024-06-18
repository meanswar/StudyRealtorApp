package com.nikitosii.studyrealtorapp.core.domain.useCase.agent

import com.nikitosii.studyrealtorapp.core.domain.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.ImageRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.image.ImageData
import javax.inject.Inject

class GetAgentDetailsUseCase @Inject constructor(
    private val repo: AgentsRepo,
    private val imageRepo: ImageRepo
) : UseCaseParams<AgentDetails, GetAgentDetailsUseCase.Params>() {
    class Params private constructor(val id: String) {
        companion object {
            fun create(id: String) = Params(id)
        }
    }

    override suspend fun execute(data: Params): AgentDetails {
        val result = repo.getAgentDetails(data.id)
        val marketingAreas = result.marketingArea.map {
            val image = getImage(it.name)
            it.copy(imageUrl = image.url)
        }
        return result.copy(marketingArea = marketingAreas)
    }

    private suspend fun getImage(request: String): ImageData {
        val localImage = imageRepo.getLocalImage(request)
        return if (localImage?.url == null) {
            val networkImage = imageRepo.getImage(request)
            imageRepo.insertImageData(networkImage)
            networkImage
        } else localImage
    }
}