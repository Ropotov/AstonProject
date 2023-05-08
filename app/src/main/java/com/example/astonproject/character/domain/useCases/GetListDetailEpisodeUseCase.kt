package com.example.astonproject.character.domain.useCases

import com.example.astonproject.character.domain.repository.CharacterRepository
import javax.inject.Inject

class GetListDetailEpisodeUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    fun getListEpisodes(list: String) = repository.getDetailEpisodeList(list)
}