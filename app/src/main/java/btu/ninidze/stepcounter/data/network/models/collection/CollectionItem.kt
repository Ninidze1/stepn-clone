package btu.ninidze.stepcounter.data.network.models.collection

data class CollectionItem(
    val favoritesCount: Int,
    val imageUrl: String,
    val name: String,
    val tokenId: String,
    val price: Int,
    val earnRate: Int
)