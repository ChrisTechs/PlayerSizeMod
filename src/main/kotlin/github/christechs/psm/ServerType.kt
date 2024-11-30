package github.christechs.psm

enum class ServerType {
    SKYBLOCK,
    LOBBY,
    OTHER;

    fun isAllowed(): Boolean = this != OTHER
}