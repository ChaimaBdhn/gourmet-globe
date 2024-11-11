import com.cocoahero.android.imagemap.ImageMap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class WorldMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_map)

        val worldMap = findViewById<ImageMap>(R.id.worldMap)
        
        // Configure the cliquable zones 
        worldMap.addShape("europe", /* coordinates */)
        worldMap.addShape("asia", /* coordinates */)
        worldMap.addShape("africa", /* coordinates */)

        // Handle clicks
        worldMap.setOnImageMapClickListener { id, _ ->
            when (id) {
                "europe" -> openRecipes("europe")
                "asia" -> openRecipes("asia")
                "africa" -> openRecipes("africa")
                "..."
            }
        }
    }

    // depending on the region clicked, retrieves corresponding recipes 
    private fun openRecipes(region: String) {
        // handling GET requests ?
    }
}
