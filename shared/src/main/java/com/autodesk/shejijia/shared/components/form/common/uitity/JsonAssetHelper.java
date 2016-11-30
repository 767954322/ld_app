package com.autodesk.shejijia.shared.components.form.common.uitity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t_panya on 16/11/7.
 */

public class JsonAssetHelper {
    /**
     * file
     */
    //inspection
        //completion
    private static final String DECORATION_COMPLETION = "inspection_decoration_completion.json";
    private static final String ELECTRIC_GAS_COMPLETION = "inspection_electric_gas_completion.json";
    private static final String WATER_COMPLETION = "inspection_water_completion.json";

        //concealed work
    private static final String BASIC_LEVEL = "inspection_basic_level.json";
    private static final String DECORATION_CONCEALED = "inspection_decoration_concealed_work.json";
    private static final String ELECTRIC_GAS_CONCEALED = "inspection_electric_gas_concealed_work.json";
    private static final String WATER_CONCEALED = "inspection_water_concealed_work.json";

        //middle
    private static final String DECORATION_MIDDLE = "inspection_decoration_middle.json";
    private static final String ELECTRIC_GAS_MIDDLE = "inspection_electric_gas_middle.json";
    private static final String FACING_BRICK = "inspection_brick_hollowness.json";
    private static final String WATER_MIDDLE= "inspection_water_middle.json";

    //material
    private static final String MATERIAL_BATHROOM = "material_bathroom.json";
    private static final String MATERIAL_BOILER = "material_boiler.json";
    private static final String MATERIAL_CALORIFIER = "material_calorifier.json";
    private static final String MATERIAL_CEILING = "material_ceiling.json";
    private static final String MATERIAL_CENTRAL_AIRCONDITIONING = "material_central_airconditioning.json";
    private static final String MATERIAL_CUPBOARD = "material_cupboard.json";
    private static final String MATERIAL_DOOR = "material_door.json";
    private static final String MATERIAL_FABRICS = "material_fabrics.json";
    private static final String MATERIAL_FLOOR = "material_floor.json";
    private static final String MATERIAL_FLOOR_HEATING = "material_floor_heating.json";
    private static final String MATERIAL_FURNITURE = "material_furniture.json";
    private static final String MATERIAL_INTELLIGENT_FURNITURE = "material_intelligent_furniture.json";
    private static final String MATERIAL_LAMPS_HOME_THEATER = "material_lamps_and_home_theater.json";
    private static final String MATERIAL_PAINTED_DIATOM = "material_painted_diatom_mud_wall.json";
    private static final String MATERIAL_PLASTER = "material_plaster.json";
    private static final String MATERIAL_RADIATOR ="material_radiator.json";
    private static final String MATERIAL_STAIRS = "material_stairs.json";
    private static final String MATERIAL_STONE = "material_stone.json";
    private static final String MATERIAL_TILE = "material_tile.json";
    private static final String MATERIAL_WALLPAPER = "material_wallpaper.json";
    private static final String MATERIAL_WATER_TREATMENT = "material_water_treatment.json";
    private static final String MATERIAL_WINDOW = "material_window.json";

    //patrol
    private static final String PATROL_BASE = "patrol_base_completion.json";
    private static final String PATROL_CONCEALED_WORK = "patrol_concealed_work.json";
    private static final String PATROL_PROJECT_COMPLETION = "patrol_project_completion.json";
    private static final String PATROL_WATER_PROOFING = "patrol_water_proofing_work.json";
    private static final String RECORDING_WATER_PROOFING = "recording_water_proofing_work.json";

    //precheck
    private static final String PRECHECK_COMPLETE_CONDITION = "precheck_complete_condition.json";
    private static final String PRECHECK_CONCEALED_CONDITION = "precheck_concealed_condition.json";
    private static final String PRECHECK_MIDDLE_CONDITION = "precheck_middle_condition.json";


    /**
     * Path
     */
    //inspection
        //completion
    private static final String PATH_DECORATION_COMPLETION = "template/inspect/completion/inspection_decoration_completion.json";
    private static final String PATH_ELECTRIC_GAS_COMPLETION = "template/inspect/completion/inspection_electric_gas_completion.json";
    private static final String PATH_WATER_COMPLETION = "template/inspect/completion/inspection_water_completion.json";

        //concealed work
    private static final String PATH_BASIC_LEVEL = "template/inspect/concealed work/inspection_basic_level.json";
    private static final String PATH_DECORATION_CONCEALED = "template/inspect/concealed work/inspection_decoration_concealed_work.json";
    private static final String PATH_ELECTRIC_GAS_CONCEALED = "template/inspect/concealed work/inspection_electric_gas_concealed_work.json";
    private static final String PATH_WATER_CONCEALED = "template/inspect/concealed work/inspection_water_concealed_work.json";

        //middle
    private static final String PATH_DECORATION_MIDDLE = "template/inspect/middle/inspection_decoration_middle.json";
    private static final String PATH_ELECTRIC_GAS_MIDDLE = "template/inspect/middle/inspection_electric_gas_middle.json";
    private static final String PATH_FACING_BRICK = "template/inspect/middle/inspection_brick_hollowness.json";
    private static final String PATH_WATER_MIDDLE= "template/inspect/middle/inspection_water_middle.json";

    //material
    private static final String PTAH_MATERIAL_BATHROOM = "template/material/material_bathroom.json";
    private static final String PATH_MATERIAL_BOILER = "template/material/material_boiler.json";
    private static final String PATH_MATERIAL_CALORIFIER = "template/material/material_calorifier.json";
    private static final String PATH_MATERIAL_CEILING = "template/material/material_ceiling.json";
    private static final String PATH_MATERIAL_CENTRAL_AIRCONDITIONING = "template/material/material_central_airconditioning.json";
    private static final String PATH_MATERIAL_CUPBOARD = "template/material/material_cupboard.json";
    private static final String PATH_MATERIAL_DOOR = "template/material/material_door.json";
    private static final String PATH_MATERIAL_FABRICS = "template/material/material_fabrics.json";
    private static final String PATH_MATERIAL_FLOOR = "template/material/material_floor.json";
    private static final String PATH_PAMATERIAL_FLOOR_HEATING = "template/material/material_floor_heating.json";
    private static final String PATH_MATERIAL_FURNITURE = "template/material/material_furniture.json";
    private static final String PATH_MATERIAL_INTELLIGENT_FURNITURE = "template/material/material_intelligent_furniture.json";
    private static final String PATH_MATERIAL_LAMPS_HOME_THEATER = "template/material/material_lamps_and_home_theater.json";
    private static final String PATH_MATERIAL_PAINTED_DIATOM = "template/material/material_painted_diatom_mud_wall.json";
    private static final String PATH_MATERIAL_PLASTER = "template/material/material_plaster.json";
    private static final String PATH_MATERIAL_RADIATOR = "template/material/material_radiator.json";
    private static final String PATH_MATERIAL_STAIRS = "template/material/material_stairs.json";
    private static final String PATH_MATERIAL_STONE = "template/material/material_stone.json";
    private static final String PATH_MATERIAL_TILE = "template/material/material_tile.json";
    private static final String PATH_MATERIAL_WALLPAPER = "template/material/material_wallpaper.json";
    private static final String PATH_MATERIAL_WATER_TREATMENT = "template/material/material_water_treatment.json";
    private static final String PATH_MATERIAL_WINDOW = "template/material/material_window.json";

    //patrol
    private static final String PATH_PATROL_BASE = "template/patrol/patrol_base_completion.json";
    private static final String PATH_PATROL_CONCEALED_WORK = "template/patrol/patrol_concealed_work.json";
    private static final String PATH_PATROL_PROJECT_COMPLETION = "template/patrol/patrol_project_completion.json";
    private static final String PATH_PATROL_WATER_PROOFING = "template/patrol/patrol_water_proofing_work.json";
    private static final String PATH_RECORDING_WATER_PROOFING = "template/patrol/recording_water_proofing_work.json";

    //precheck
    private static final String PATH_PRECHECK_COMPLETE_CONDITION = "template/precheck/precheck_complete_condition.json";
    private static final String PATH_PRECHECK_CONCEALED_CONDITION = "template/precheck/precheck_concealed_condition.json";
    private static final String PATH_PRECHECK_MIDDLE_CONDITION = "template/precheck/precheck_middle_condition.json";

    public static Map<String,String> routerJSON2Path(){
        Map<String,String> jsonToPathMap = new HashMap<>();
        //completion
        jsonToPathMap.put(DECORATION_COMPLETION,PATH_DECORATION_COMPLETION);
        jsonToPathMap.put(ELECTRIC_GAS_COMPLETION,PATH_ELECTRIC_GAS_COMPLETION);
        jsonToPathMap.put(WATER_COMPLETION,PATH_WATER_COMPLETION);
        //concealed
        jsonToPathMap.put(BASIC_LEVEL,PATH_BASIC_LEVEL);
        jsonToPathMap.put(DECORATION_CONCEALED,PATH_DECORATION_CONCEALED);
        jsonToPathMap.put(ELECTRIC_GAS_CONCEALED,PATH_ELECTRIC_GAS_CONCEALED);
        jsonToPathMap.put(WATER_CONCEALED,PATH_WATER_CONCEALED);
        //middle
        jsonToPathMap.put(DECORATION_MIDDLE,PATH_DECORATION_MIDDLE);
        jsonToPathMap.put(ELECTRIC_GAS_MIDDLE,PATH_ELECTRIC_GAS_MIDDLE);
        jsonToPathMap.put(FACING_BRICK,PATH_FACING_BRICK);
        jsonToPathMap.put(WATER_MIDDLE,PATH_WATER_MIDDLE);
        //material
        jsonToPathMap.put(MATERIAL_BATHROOM,PTAH_MATERIAL_BATHROOM);
        jsonToPathMap.put(MATERIAL_BOILER,PATH_MATERIAL_BOILER);
        jsonToPathMap.put(MATERIAL_CALORIFIER,PATH_MATERIAL_CALORIFIER);
        jsonToPathMap.put(MATERIAL_CEILING,PATH_MATERIAL_CEILING);
        jsonToPathMap.put(MATERIAL_CENTRAL_AIRCONDITIONING,PATH_MATERIAL_CENTRAL_AIRCONDITIONING);
        jsonToPathMap.put(MATERIAL_CUPBOARD,PATH_MATERIAL_CUPBOARD);
        jsonToPathMap.put(MATERIAL_DOOR,PATH_MATERIAL_DOOR);
        jsonToPathMap.put(MATERIAL_FABRICS,PATH_MATERIAL_FABRICS);
        jsonToPathMap.put(MATERIAL_FLOOR,PATH_MATERIAL_FLOOR);
        jsonToPathMap.put(MATERIAL_FLOOR_HEATING,PATH_PAMATERIAL_FLOOR_HEATING);
        jsonToPathMap.put(MATERIAL_FURNITURE,PATH_MATERIAL_FURNITURE);
        jsonToPathMap.put(MATERIAL_INTELLIGENT_FURNITURE,PATH_MATERIAL_INTELLIGENT_FURNITURE);
        jsonToPathMap.put(MATERIAL_LAMPS_HOME_THEATER,PATH_MATERIAL_LAMPS_HOME_THEATER);
        jsonToPathMap.put(MATERIAL_PAINTED_DIATOM,PATH_MATERIAL_PAINTED_DIATOM);
        jsonToPathMap.put(MATERIAL_PLASTER,PATH_MATERIAL_PLASTER);
        jsonToPathMap.put(MATERIAL_RADIATOR,PATH_MATERIAL_RADIATOR);
        jsonToPathMap.put(MATERIAL_STAIRS,PATH_MATERIAL_STAIRS);
        jsonToPathMap.put(MATERIAL_STONE,PATH_MATERIAL_STONE);
        jsonToPathMap.put(MATERIAL_TILE,PATH_MATERIAL_TILE);
        jsonToPathMap.put(MATERIAL_WALLPAPER,PATH_MATERIAL_WALLPAPER);
        jsonToPathMap.put(MATERIAL_WATER_TREATMENT,PATH_MATERIAL_WATER_TREATMENT);
        jsonToPathMap.put(MATERIAL_WINDOW,PATH_MATERIAL_WINDOW);
        //patrol
        jsonToPathMap.put(PATROL_BASE,PATH_PATROL_BASE);
        jsonToPathMap.put(PATROL_CONCEALED_WORK,PATH_PATROL_CONCEALED_WORK);
        jsonToPathMap.put(PATROL_PROJECT_COMPLETION,PATH_PATROL_PROJECT_COMPLETION);
        jsonToPathMap.put(PATROL_WATER_PROOFING,PATH_PATROL_WATER_PROOFING);
        jsonToPathMap.put(RECORDING_WATER_PROOFING,PATH_RECORDING_WATER_PROOFING);
        //precheck
        jsonToPathMap.put(PRECHECK_COMPLETE_CONDITION,PATH_PRECHECK_COMPLETE_CONDITION);
        jsonToPathMap.put(PRECHECK_CONCEALED_CONDITION,PATH_PRECHECK_CONCEALED_CONDITION);
        jsonToPathMap.put(PRECHECK_MIDDLE_CONDITION,PATH_PRECHECK_MIDDLE_CONDITION);
        return jsonToPathMap;
    }
}
