package io.github.louistsaitszho.lineage.model

import io.github.louistsaitszho.lineage.model.poko.Data
import io.github.louistsaitszho.lineage.model.poko.attributes.SchoolAttribute

/**
 * Created by louis on 26.11.17.
 */
data class School(
        val id: String,
        val name: String
) {
    constructor(apiResponse: Data<SchoolAttribute>) : this(apiResponse.id, apiResponse.attributes.name)
}