package io.github.louistsaitszho.lineage.model

import io.github.louistsaitszho.lineage.model.attributes.ModuleAttribute
import io.github.louistsaitszho.lineage.model.poko.Data

/**
 * Created by louis on 09.11.17.
 */
data class Module(
        val id: String,
        val name: String
) {
    constructor(apiResponse: Data<ModuleAttribute>) : this(apiResponse.id, apiResponse.attributes.name)
}