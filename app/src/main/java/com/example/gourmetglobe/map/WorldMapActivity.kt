import com.cocoahero.android.imagemap.ImageMap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class WorldMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_map)

        val worldMap = findViewById<ImageMap>(R.id.worldMap)
        
        // Configure les zones cliquables
        worldMap.addShape("europe", /* coordonnées de la zone pour l'Europe */)
        worldMap.addShape("asia", /* coordonnées de la zone pour l'Asie */)
        worldMap.addShape("africa", /* coordonnées de la zone pour l'Afrique */)

        // Gère les clics
        worldMap.setOnImageMapClickListener { id, _ ->
            when (id) {
                "europe" -> openRecipes("europe")
                "asia" -> openRecipes("asia")
                "africa" -> openRecipes("africa")
                // Ajoute d'autres régions selon tes besoins
            }
        }
    }

    private fun openRecipes(region: String) {
        // Lance une nouvelle activité ou fragment avec les recettes de la région
        // Par exemple, en passant l'identifiant de la région à la nouvelle activité
    }
}
