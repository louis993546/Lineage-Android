package io.github.louistsaitszho.lineage.model.poko

/**
 * Created by louis on 09.11.17.
 */
data class JsonApiResponse<out Attribute>(
        val data: List<Data<Attribute>>?,
        val error: List<Error>?
)