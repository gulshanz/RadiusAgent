package taksande.gulshan.radiusagent.data.model

import androidx.room.Entity

@Entity
data class ApiResponse(
    val exclusions: List<List<Exclusion>>,
    val facilities: List<Facility>
)