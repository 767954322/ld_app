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
    public static final String DECORATION_COMPLETION = "inspection_decoration_completion.json";
    public static final String ELECTRIC_GAS_COMPLETION = "inspection_electric_gas_completion.json";
    public static final String WATER_COMPLETION = "inspection_water_completion";

        //concealed work
    public static final String BASIC_LEVEL = "inspection_basic_level.json";
    public static final String DECORATION_CONCEALED = "inspection_decoration_concealed_work.json";
    public static final String ELECTRIC_GAS_CONCEALED = "inspection_electric_gas_concealed_work.json";
    public static final String WATER_CONCEALED = "inspection_water_concealed_work.json";

        //middle
    public static final String DECORATION_MIDDLE = "inspection_decoration_middle.json";
    public static final String ELECTRIC_GAS_MIDDLE = "inspection_electric_gas_middle.json";
    public static final String FACING_BRICK = "inspection_facing_brick.json";
    public static final String WATER_MIDDLE= "inspection_water_middle.json";

    //material
    public static final String MATERIAL_BATHROOM = "material_bathroom.json";
    public static final String MATERIAL_BOILER = "material_boiler.json";
    public static final String MATERIAL_CALORIFIER = "material_calorifier.json";
    public static final String MATERIAL_CEILING = "material_ceiling.json";
    public static final String MATERIAL_CENTRAL_AIRCONDITIONING = "material_central_airconditioning.json";
    public static final String MATERIAL_CUPBOARD = "material_cupboard.json";
    public static final String MATERIAL_DOOR = "material_door";
    public static final String MATERIAL_FABRICS = "material_fabrics.json";
    public static final String MATERIAL_FLOOR = "material_floor.json";
    public static final String MATERIAL_FLOOR_HEATING = "material_floor_heating.json";
    public static final String MATERIAL_FURNITURE = "material_furniture.json";
    public static final String MATERIAL_INTELLIGENT_FURNITURE = "material_intelligent_furniture.json";
    public static final String MATERIAL_LAMPS_HOME_THEATER = "material_lamps_and_home_theater.json";
    public static final String MATERIAL_PAINTED_DIATOM = "material_painted_diatom_mud_wall.json";
    public static final String MATERIAL_PLASTER = "material_plaster.json";
    public static final String MATERIAL_RADIATOR ="material_radiator.json";
    public static final String MATERIAL_STAIRS = "material_stairs.json";
    public static final String MATERIAL_STONE = "material_stone.json";
    public static final String MATERIAL_TILE = "material_tile.json";
    public static final String MATERIAL_WALLPAPER = "material_wallpaper.json";
    public static final String MATERIAL_WATER_TREATMENT = "material_water_treatment.json";
    public static final String MATERIAL_WINDOW = "material_window.json";

    //patrol
    public static final String PATROL_BASE = "patrol_base_completion.json";
    public static final String PATROL_CONCEALED_WORK = "patrol_concealed_work.json";
    public static final String PATROL_PROJECT_COMPLETION = "patrol_project_completion.json";
    public static final String PATROL_WATER_PROOFING = "patrol_water_proofing_work.json";
    public static final String RECORDING_WATER_PROOFING = "recording_patrol_water_proofing_work.json";

    //precheck
    public static final String PRECHECK_COMPLETE_CONDITION = "precheck_complete_condition.json";
    public static final String PRECHECK_CONCEALED_CONDITION = "precheck_concealed_condition.json";
    public static final String PRECHECK_MIDDLE_CONDITION = "precheck_middle_condition.json";


    /**
     * Path
     */
    //inspection
        //completion
    public static final String PATH_DECORATION_COMPLETION = "forminstance/template/inspect/completion/inspection_decoration_completion.json";
    public static final String PATH_ELECTRIC_GAS_COMPLETION = "forminstance/template/inspect/completion/inspection_electric_gas_completion.json";
    public static final String PATH_WATER_COMPLETION = "forminstance/template/inspect/completion/inspection_water_completion";

        //concealed work
    public static final String PATH_BASIC_LEVEL = "forminstance/template/inspect/concealed work/inspection_basic_level.json";
    public static final String PATH_DECORATION_CONCEALED = "forminstance/template/inspect/concealed work/inspection_decoration_concealed_work.json";
    public static final String PATH_ELECTRIC_GAS_CONCEALED = "forminstance/template/inspect/concealed work/inspection_electric_gas_concealed_work.json";
    public static final String PATH_WATER_CONCEALED = "forminstance/template/inspect/concealed work/inspection_water_concealed_work.json";

        //middle
    public static final String PATH_DECORATION_MIDDLE = "forminstance/template/inspect/middle/inspection_decoration_middle.json";
    public static final String PATH_ELECTRIC_GAS_MIDDLE = "forminstance/template/inspect/middle/inspection_electric_gas_middle.json";
    public static final String PATH_FACING_BRICK = "forminstance/template/inspect/middle/inspection_facing_brick.json";
    public static final String PATH_WATER_MIDDLE= "forminstance/template/inspect/middle/inspection_water_middle.json";

    //material
    public static final String PTAH_MATERIAL_BATHROOM = "forminstance/template/material/material_bathroom.json";
    public static final String PATH_MATERIAL_BOILER = "forminstance/template/material/material_boiler.json";
    public static final String PATH_MATERIAL_CALORIFIER = "forminstance/template/material/material_calorifier.json";
    public static final String PATH_MATERIAL_CEILING = "forminstance/template/material/material_ceiling.json";
    public static final String PATH_MATERIAL_CENTRAL_AIRCONDITIONING = "forminstance/template/material/material_central_airconditioning.json";
    public static final String PATH_MATERIAL_CUPBOARD = "forminstance/template/material/material_cupboard.json";
    public static final String PATH_MATERIAL_DOOR = "forminstance/template/material/material_door";
    public static final String PATH_MATERIAL_FABRICS = "forminstance/template/material/material_fabrics.json";
    public static final String PATH_MATERIAL_FLOOR = "forminstance/template/material/material_floor.json";
    public static final String PATH_PAMATERIAL_FLOOR_HEATING = "forminstance/template/material/material_floor_heating.json";
    public static final String PATH_MATERIAL_FURNITURE = "forminstance/template/material/material_furniture.json";
    public static final String PATH_MATERIAL_INTELLIGENT_FURNITURE = "forminstance/template/material/material_intelligent_furniture.json";
    public static final String PATH_MATERIAL_LAMPS_HOME_THEATER = "forminstance/template/material/material_lamps_and_home_theater.json";
    public static final String PATH_MATERIAL_PAINTED_DIATOM = "forminstance/template/material/material_painted_diatom_mud_wall.json";
    public static final String PATH_MATERIAL_PLASTER = "forminstance/template/material/material_plaster.json";
    public static final String PATH_MATERIAL_RADIATOR ="forminstance/template/material/material_radiator.json";
    public static final String PATH_MATERIAL_STAIRS = "forminstance/template/material/material_stairs.json";
    public static final String PATH_MATERIAL_STONE = "forminstance/template/material/material_stone.json";
    public static final String PATH_MATERIAL_TILE = "forminstance/template/material/material_tile.json";
    public static final String PATH_MATERIAL_WALLPAPER = "forminstance/template/material/material_wallpaper.json";
    public static final String PATH_MATERIAL_WATER_TREATMENT = "forminstance/template/material/material_water_treatment.json";
    public static final String PATH_MATERIAL_WINDOW = "forminstance/template/material/material_window.json";

    //patrol
    public static final String PATH_PATROL_BASE = "forminstance/template/patrol/patrol_base_completion.json";
    public static final String PATH_PATROL_CONCEALED_WORK = "forminstance/template/patrol/patrol_concealed_work.json";
    public static final String PATH_PATROL_PROJECT_COMPLETION = "forminstance/template/patrol/patrol_project_completion.json";
    public static final String PATH_PATROL_WATER_PROOFING = "forminstance/template/patrol/patrol_water_proofing_work.json";
    public static final String PATH_RECORDING_WATER_PROOFING = "forminstance/template/patrol/recording_patrol_water_proofing_work.json";

    //precheck
    public static final String PATH_PRECHECK_COMPLETE_CONDITION = "forminstance/template/precheck/precheck_complete_condition.json";
    public static final String PATH_PRECHECK_CONCEALED_CONDITION = "forminstance/template/precheck/precheck_concealed_condition.json";
    public static final String PATH_PRECHECK_MIDDLE_CONDITION = "forminstance/template/precheck/precheck_middle_condition.json";

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
