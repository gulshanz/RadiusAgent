package taksande.gulshan.radiusagent.data.model

import androidx.room.Entity

@Entity
data class Facility(
    val facility_id: String,
    val name: String,
    val options: List<Option>
)