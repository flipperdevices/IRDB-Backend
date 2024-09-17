package com.flipperdevices.ifrmvp.backend.model

/**
 * [DeviceKey] is default set of pre-defined keys for remote controls
 *
 * The keys have comments in which devices they are present
 */
enum class DeviceKey(val desc: String) {
    PWR("Power enable button"),
    SHUTTER("Take a photo with camera"),
    VOL_UP("Increase volume"),
    VOL_DOWN("Decrease volume"),
    CH_UP("Next channel"),
    CH_DOWN("Previous channel"),
    FOCUS_MORE("Add focus"),
    FOCUS_LESS("Decrease focus"),
    ZOOM_UP("Zoom up"),
    ZOOM_DOWN("Zoom down"),
    RESET("Reset"),
    DOWN("Navigation button(for TV menu, for example) - down"),
    UP("Navigation button(for TV menu, for example) - up"),
    RIGHT("Navigation button(for TV menu, for example) - right"),
    LEFT("Navigation button(for TV menu, for example) - left"),
    NEXT("Next button for TV box"),
    PREVIOUS("Previous button for TV box"),
    TV("TV button fot TVs"),
    AUX("Change AUX mode"),
    HOME("Home button"),
    BACK("Back button"),
    MENU("Show/Open menu"),
    PLAY("Play/Resume playback"),
    MUTE("Mute sound"),
    EJECT("Eject disk"),
    FAN_SPEED("Change fan speed recursively"),
    NEAR("Near camera clipping plane"),
    FAR("Far camera clipping plane"),
    PAUSE("Pause playback"),
    RECORD("Start recording"),
    WIND_SPEED("Change wind speed recursively"),
    MODE("Change mode (TV Box, DVD)"),
    LIGHT("Increase/Decrease light recursively"),
    FAN_MEDIUM("Set fan speed to medium"),
    FAN_HIGH("Set fan mode to high"),
    FAN_LOW("Change fan speed to low"),
    STOP("Stop button (mostly for TV Box)"),
    EXIT("Exit(from menu, etc)"),
    INFO("Show info"),
    TIMER("Start timer, show timer menu"),
    OSCILLATE("Start oscillating (mostly for fans)"),
    TIMER_ADD("Increase timer value"),
    TIMER_REDUCE("Decrease timer value"),
    FAN_SPEED_UP("Increase fan speed"),
    FAN_SPEED_DOWN("Decrease fan speed"),
    SLEEP("Start sleep mode"),
    SHAKE_WIND("Same as oscillate, start shaking"),
    SWING("Same as oscillate, start swing"),
    OFF("Turn off the device"),
    BRIGHTNESS_UP("Increase brightness"),
    BRIGHTNESS_DOWN("Decrease brightness"),
    COLD_WIND("Set wind cold"),
    COOL("Set/increase cool"),
    WIND_TYPE("Change wind type"),
    TEMPERATURE_UP("Increase temperature"),
    TEMPERATURE_DOWN("Decrease temperature"),
    HEAT_ADD("Add heat"),
    HEAT_REDUCE("Reduce heat"),
    ENERGY_SAVE("Enter energy save mode"),
    OK("Ok button(mostly where up/left/right/down buttons located"),
    REW("Rewind button(mostly for playback)"),
    SET("Set button(mostly for TV box)"),
    DELETE("Delete button(mostly for TV Box)"),
    VOD("Enable VODs"),
    LIVE_TV("Set TV mode"),
    FAVORITE("Add to favorite"),
    INPUT("Input button to change input source(hdmi, tv, etc)")
}

// Generate docs text
fun main() {
    DeviceKey.entries.forEach {
        println("- `${it.name}` -> ${it.desc}")
    }
}
