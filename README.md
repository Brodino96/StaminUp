# StaminUp

![banner](images/staminup_title.png)

A Minecraft Fabric mod that adds a stamina system to the game.
Players have a stamina bar that depletes when jumping and regenerates passively over time.
If a player runs out of stamina, they cannot jump until it recovers.

---

## Features

- **Stamina bar** displayed as an animated HUD icon above the hotbar (visible in survival and adventure mode only)
- **Jump cost** — each jump depletes a configurable amount of stamina. Jumping is blocked when stamina is insufficient
- **Passive regeneration** — stamina refills automatically at a configurable per second rate
- **Optional movement check** — stamina cost can be restricted to sprinting only
- **5 custom status effects** that interact with stamina:

  | Effect          | Type       | Behavior                                                 |
  |-----------------|------------|----------------------------------------------------------|
  | Vigor           | Beneficial | Increases stamina regen rate by 10% per level            |
  | Fatigue         | Harmful    | Decreases stamina regen rate by 10% per level            |
  | Endurance       | Beneficial | Increases max stamina by 10% per level                   |
  | Exhaustion      | Harmful    | Decreases max stamina by 10% per level                   |
  | Instant Stamina | Beneficial | Instantly restores 10% stamina per level, capped at 100% |

- **Hot-reload config** — update settings at runtime without restarting the server

---

## Configuration

On first launch, the mod generates a config file at:

```
config/staminup.json
```

**Default values:**

```json
{
  "maxStamina": 100.0,
  "staminaRegen": 2.0,
  "jumpCost": 10.0,
  "movementCheck": false
}
```

| Key             | Default | Description                                              |
|-----------------|---------|----------------------------------------------------------|
| `maxStamina`    | `100.0` | Maximum stamina pool (must be > 0)                       |
| `staminaRegen`  | `2.0`   | Stamina restored per second (must be > 0)                |
| `jumpCost`      | `10.0`  | Stamina consumed per jump (must be > 0 and ≤ maxStamina) |
| `movementCheck` | `false` | If `true`, stamina cost only applies while sprinting     |

Invalid values fall back to defaults automatically.

### Reloading config

To apply config changes without restarting the server, run:

```
/staminup reloadConfig
```

Requires operator permission level 2. The updated config is pushed to all connected clients immediately. Config is also reloaded automatically when running `/reload`

---

## Building from Source

**Prerequisites:** JDK 17+

```bash
# Build the mod JAR
./gradlew build
# Output: build/libs/staminup-<version>.jar

# Run a development client
./gradlew runClient

# Run a development server
./gradlew runServer

# Generate Minecraft sources for IDE navigation
./gradlew genSources
```

---

## License

[MIT](LICENSE.txt)
