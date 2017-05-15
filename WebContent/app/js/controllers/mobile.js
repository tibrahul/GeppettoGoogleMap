app.controller('MobileController', ['$scope', '$timeout', '$compile', '$cookieStore', '$location', '$routeParams',
  '$http', '$modal', 'ActivityInformation', 'RestURL',
  function ($scope, $timeout, $compile, $cookieStore, $location, $routeParams, $http, $modal, ActivityInfo, RestURL) {

    $scope.name = 'mobile';
    //$scope.showAddWizardBtn = true;
    common($scope, $timeout, $compile, $cookieStore, $location, $routeParams, $http, $modal, ActivityInfo, RestURL);

    $scope.element_camera_destinationTypes =[
                                             {"label":"Camera.DestinationType.FILE_URI","value":"Camera.DestinationType.FILE_URI"},
                                             {"label":"Camera.DestinationType.DATA_URL","value":"Camera.DestinationType.DATA_URL"}
                                             ];
    $scope.overlapping = false;
    $scope.outsidescreen = false;
    
    /* create or update */
    switch ($routeParams['action']) {
      case 'create':
    	$scope.showBtn=false;
        $scope.submit = 'Create';
        $cookieStore.put('back', '/en-US/axe/mobile/create/');
        //$scope.showAddWizardBtn=true;
        /* set Activity and Device information */
        if (!$.isEmptyObject(ActivityInfo.activity) && !$.isEmptyObject(ActivityInfo.deviceTypes)) {
          console.log('Activity Information: ');
          console.log(ActivityInfo.activity);

          console.log('Device Types: ');
          console.log(ActivityInfo.deviceTypes);

          /* set device information */
          nodeTree['screen']['client_device_type'] = ActivityInfo.deviceTypes.client_device_type;
          nodeTree['screen']['client_device_type_id'] = ActivityInfo.deviceTypes.id;
          nodeTree['screen']['client_device_type_name'] = ActivityInfo.deviceTypes.client_device_type_name;
          nodeTree['screen']['client_device_type_label'] = ActivityInfo.deviceTypes.client_device_type_label;
          nodeTree['screen']['client_device_type_description'] = ActivityInfo.deviceTypes.client_device_type_description;
          nodeTree['screen']['client_device_screen_size'] = ActivityInfo.deviceTypes.client_device_screen_size;
          nodeTree['screen']['client_device_resolution'] = ActivityInfo.deviceTypes.client_device_resolution;
          nodeTree['screen']['client_device_ppcm'] = ActivityInfo.deviceTypes.client_device_ppcm;
          nodeTree['screen']['client_device_type_os_name'] = ActivityInfo.deviceTypes.client_device_type_os_name;
          nodeTree['screen']['client_device_type_os_version'] = ActivityInfo.deviceTypes.client_device_type_os_version;
          nodeTree['screen']['portrait_image_name'] = ActivityInfo.deviceTypes.portrait_image_name;
          nodeTree['screen']['landscape_image_name'] = ActivityInfo.deviceTypes.landscape_image_name;

          /* set orientation data */
          nodeTree['screen']['orientation'] = orientation;

          /* set activity data */
          nodeTree['screen']['activity_id'] = ActivityInfo.activity.id;

          /* set primary noun data */
          nodeTree['screen']['primary_noun_id'] = 0;
          if (!$.isEmptyObject(ActivityInfo.activity.primary_noun)) {
            nodeTree['screen']['primary_noun_id'] = ActivityInfo.activity.primary_noun.id;
          } else console.log('primary noun is empty');

          /* set secondary noun data */
          nodeTree['screen']['secondary_noun_ids'] = [];
          if (!$.isEmptyObject(ActivityInfo.activity.secondary_nouns)) {
            for (var i = 0; i < ActivityInfo.activity.secondary_nouns.length; ++i) {
              nodeTree['screen']['secondary_noun_ids'].push(ActivityInfo.activity.secondary_nouns[i].id.toString());
            }
          } else console.log('secondary nouns are empty');

          /* reset screen event_verb_combo */
          nodeTree['screen']['event_verb_combo'] = '';

          /* set project data */
          nodeTree['screen']['projectid'] = ActivityInfo.activity.projectid;

          /* Boopathi */
          /* Activity id for screen name validation */
          $scope.activity_id = nodeTree['screen']['activity_id'];

          /* clean children */
          nodeTree['screen']['children'] = {};

          /* clean deleted widgets */
          nodeTree['screen']['deleted_widgets'] = [];
        }

        /* show Copy Screen form */
        $('.copy-screen').removeClass('hidden');

        /* create screen */
        $('.submit').on('click', function () {
          /* disable button to prevent duplicate screens */
          $(this).prop('disabled', true);

          $scope.setScreenInfo();

          /* Boopathi */
          if (!validateAllComponentsName()) {
            /* enable create button */
            $(this).prop('disabled', false);
            return false;
          }
          if (!validateCameraVideoNounBinding()) {
              /* enable update button */
              $(this).prop('disabled', false);
              return false;
            }

          console.log('Creating...');
          console.log(JSON.stringify(nodeTree['screen']));
          console.log('Data of node screen role in creating...',JSON.stringify(nodeTree['screen']['role']));
                   var rolefromMobileScreenCreate = nodeTree['screen']['role'];
                    //console.log('rolefromScreenCreate data =>',rolefromMobileScreenCreate);
                    var roleForMob = empty(rolefromMobileScreenCreate);
                    //console.log('roleForMob =>',roleForMob);
                    function empty(e) {
                  	  switch (e) {
                  	  //console.log('e value',e);
                  	    case undefined:
                  	    	//console.log('undefined dont touch na');
                  	    	return true;
                  	    case "":
                  	    	//console.log(' "" ');
                  	    	return true;
                  	    case "ROLE_USER":
                 	    	//console.log('rol use');
                  	    	return false;
                  	    case "ROLE_ADMIN":
                  	    	//console.log('role ad');
                  	    	return false;
                  	    case null:
                  	    	//console.log('null');
                  	    	return true;
                  	    default:
                  	    	//console.log('Default executed')
                  	      return true;
                  	  }
                  	}
                    if(roleForMob){
                  	  nodeTree['screen']['role'] = 'ROLE_USER';
                    }
          $http.post(RestURL.baseURL + 'android/create_screen/', nodeTree['screen'])
            .success(function () {
              console.log('redirecting...');
              $location.url($scope.back);
            })
            .error(function () {
              console.log('Unable to create screen');
              $(this).prop('disabled', false);
            });
        });

        break;
      case 'update':
        $scope.submit = 'Update';
        $cookieStore.put('back', '/en-US/axe/mobile/update/');
        $scope.activity=ActivityInfo.activity;
        $scope.showAddWizardBtn=true;
        if (!$.isEmptyObject(ActivityInfo.deviceTypes)) {
          $scope.screen_id = ActivityInfo.deviceTypes.id;
          console.log('normal update');
        } else {
          $scope.screen_id = undefined;
          alert('Unable to get screen id');
        }

        /* get json for update */
        $http.get(RestURL.baseURL + 'android/search_for_update/?screen_id=' + $scope.screen_id)
          .success(function (data) {
            console.log('update json');
            console.log(JSON.stringify(data));
            if($scope.activity.wizard_phone_screens.length>0){
            	for(var i=0;i<$scope.activity.wizard_phone_screens.length;i++){
            		if($scope.activity.wizard_phone_screens[i].id==data.id){
            			$scope.showAddWizardBtn=false;
            		}
            	}
            }/*else{
            	$scope.showAddWizardBtn=false;
            }*/
            UpdateScreen($scope, $compile, data);
          })
          .error(function () {
            console.log('Unable to load screen data');
          });

        /* update screen */
        $('.submit').on('click', function () {
          /* disable button to prevent duplicate screens */
          $(this).prop('disabled', true);

          $scope.setScreenInfo();

          /* Boopathi */
          if (!validateAllComponentsName()) {
            /* enable update button */
            $(this).prop('disabled', false);
            return false;
          }

          if (!validateCameraVideoNounBinding()) {
              /* enable update button */
              $(this).prop('disabled', false);
              return false;
            }

          console.log('Updating...');
          console.log(nodeTree['screen']);
          console.log("data to update"+JSON.stringify(nodeTree['screen']));
          console.log('Data of node screen role in creating...',JSON.stringify(nodeTree['screen']['role']));
                   var rolefromMobileScreenUpdate = nodeTree['screen']['role'];
                    //console.log('rolefromMobileScreenUpdate data =>',rolefromMobileScreenUpdate);
                    var roleForMob = empty(rolefromMobileScreenUpdate);
                    //console.log('roleForMob =>',roleForMob);
                    function empty(e) {
                  	  switch (e) {
                  	  //console.log('e value',e);
                  	    case undefined:
                  	    	//console.log('undefined dont touch na');
                  	    	return true;
                  	    case "":
                  	    	//console.log(' "" ');
                  	    	return true;
                  	    case "ROLE_USER":
                  	    	//console.log('rol use');
                  	    	return false;
                  	    case "ROLE_ADMIN":
                  	    	//console.log('role ad');
                  	    	return false;
                  	    case null:
                  	    	//console.log('null');
                  	    	return true;
                  	    default:
                  	    	//console.log('Default executed')
                  	      return true;
                  	  }
                  	}
                    if(roleForMob){
                  	  nodeTree['screen']['role'] = 'ROLE_USER';
                    }
          $http.post(RestURL.baseURL + 'android/update_screen/', nodeTree['screen'])
            .success(function () {
              console.log('redirecting...');
              $location.url($scope.back);
            })
            .error(function () {
              console.log('Unable to update screen');
              /* enable update button */
              $(this).prop('disabled', false);
            });
        });

        break;
      default:
        alert('404');
        /* TODO: redirect without error */

        return;
    }

    /* deselect all elements */
    $('#screen').on('click', function (event) {
      if (event.target !== this) return;

      $('.selected').removeClass('selected');
      $scope.clearElementInfo();

      /* hide delete element button */
      $('.delete').removeClass('visible');

      /* hide grid panel */
      $('#grid-panel').addClass('hidden');

      /* Boopathi */
      /* hide tab panel */
      $('#tab-panel').addClass('hidden');

      /* hide card panel */
      $('#card-panel').addClass('hidden');

      /* hide radio panel */
      $('#radio-panel').addClass('hidden');

      /* hide list panel */
      $('#list-panel').addClass('hidden');
      
      /* hide map prop */
      $('#map-default').addClass('hidden');
    });

    /* change orientation */
    $('#portrait').on('click', function () {
      $('#landscape').removeClass('active');
      $(this).addClass('active');
      changeOrientation('portrait');
    });
    $('#landscape').on('click', function () {
      $('#portrait').removeClass('active');
      $(this).addClass('active');
      changeOrientation('landscape');
    });

    $scope.getElementInfo = function (element) {
    	
      /* show element data in Components tab */
      if ($('#screen').find(element).length) {
        $scope.$apply(function () {
          $scope.element_id = $(element).parents('.resizable').attr('id');
          $scope.element = find($scope.element_id, nodeTree['screen']['children']);
          $scope.element_name = find($scope.element_id, nodeTree['screen']['children'])['name'];
         $scope.element_label = find($scope.element_id, nodeTree['screen']['children'])['label'];
          $scope.element_description = find($scope.element_id, nodeTree['screen']['children'])['description'];
          /* TODO: use CKEditor */
          $scope.element_notes = find($scope.element_id, nodeTree['screen']['children'])['notes'];

          $scope.extended_attributes = find($scope.element_id, nodeTree['screen']['children'])['extended_attributes'];
        	 
          var index = $scope.element_name.indexOf("Range_");
          
        	 if($scope.extended_attributes!=null && $scope.extended_attributes!="" && index == -1){
        		 $scope.extended_attributes = $scope.extended_attributes.split(',');
        		 console.log("ELEMENT.........."+$scope.extended_attributes);
        		 var quality = $scope.extended_attributes[0];
                 var destType = $scope.extended_attributes[1];
                 /* show selected quality */
                 $scope.cameraproperties.element_camera_quality = quality;
                 /* show selected dest type */
                 $scope.cameraproperties.element_camera_destinationType = destType;
                 console.log($scope.cameraproperties.element_camera_destinationType);
        	 }else if($scope.extended_attributes!=null && $scope.extended_attributes!="" && index > -1){
        		 
        		 $scope.extended_attributes = $scope.extended_attributes.split(',');
        		 console.log("ELEMENT.........."+$scope.extended_attributes);
        		 var min = $scope.extended_attributes[0];
                 var max = $scope.extended_attributes[1];
                 var value = $scope.extended_attributes[2];
                 
                 $scope.rangeproperties.element_min = min;
                 $scope.rangeproperties.element_max = max;
                 $scope.rangeproperties.element_value = value;
        	 }
        	 
        	 $scope.element_data_binding_target_noun = find($scope.element_id, nodeTree['screen']['children'])['data_binding_target_noun_id'];
             $scope.element_data_binding_target_noun_attr = find($scope.element_id, nodeTree['screen']['children'])['data_binding_target_noun_attr_id']; 
             if($scope.element_data_binding_target_noun > 0){
                 $scope.showBtn=true;
                 ActivityInfo.associateNounData={};
                     $http.get(RestURL.baseURL + 'noun/get_a_noun/?noun_id=' + $scope.element_data_binding_target_noun
                     ).success(function (response) {
                   	  ActivityInfo.associateNounData.primary_noun=response;
                   	  if($scope.element_data_binding_target_noun_attr > 0 ){
                         	for(var i = 0; i < ActivityInfo.associateNounData.primary_noun.nounattributes.length; i++){
                         		if(ActivityInfo.associateNounData.primary_noun.nounattributes[i].id == $scope.element_data_binding_target_noun_attr){
                         			ActivityInfo.associateNounData.pn_attribute=ActivityInfo.associateNounData.primary_noun.nounattributes[i];
                         		}
                         	}
                         }
                     }).error(function (response) {
                       $log.log('Error : ', response);
                     });
         	  }else{
         			$scope.showBtn=false;
         	  }
             
          $scope.element_type = $(element).parents('.resizable').children().not('span.resize-handle').data('role');
          switch (window.orientation) {
            case 'portrait':
              $scope.element_portrait_width = ~~$(element).parents('.resizable').width();
              $scope.element_portrait_height = ~~$(element).parents('.resizable').height();
              $scope.element_landscape_width = find($scope.element_id, nodeTree['screen']['children'])['landscape_width'] || $scope.element_portrait_width;
              $scope.element_landscape_height = find($scope.element_id, nodeTree['screen']['children'])['landscape_height'] || $scope.element_portrait_height;

              $scope.element_portrait_x = ~~($('#' + $scope.element_id).offset().left - $('#screen').offset().left);
              $scope.element_portrait_y = ~~($('#' + $scope.element_id).offset().top - $('#screen').offset().top);
              $scope.element_landscape_x = find($scope.element_id, nodeTree['screen']['children'])['landscapeX'] || $scope.element_portrait_x;
              $scope.element_landscape_y = find($scope.element_id, nodeTree['screen']['children'])['landscapeY'] || $scope.element_portrait_y;

              break;
            case 'landscape':
              $scope.element_landscape_width = ~~$(element).parents('.resizable').width();
              $scope.element_landscape_height = ~~$(element).parents('.resizable').height();
              $scope.element_portrait_width = find($scope.element_id, nodeTree['screen']['children'])['portrait_width'] || $scope.element_landscape_width;
              $scope.element_portrait_height = find($scope.element_id, nodeTree['screen']['children'])['portrait_height'] || $scope.element_landscape_height;

              $scope.element_landscape_x = ~~($('#' + $scope.element_id).offset().left - $('#screen').offset().left);
              $scope.element_landscape_y = ~~($('#' + $scope.element_id).offset().top - $('#screen').offset().top);
              $scope.element_portrait_x = find($scope.element_id, nodeTree['screen']['children'])['portraitX'] || $scope.element_landscape_x;
              $scope.element_portrait_y = find($scope.element_id, nodeTree['screen']['children'])['portraitY'] || $scope.element_landscape_y;

              break;
          }

          find($scope.element_id, nodeTree['screen']['children'])['portrait_width'] = $scope.element_portrait_width;
          find($scope.element_id, nodeTree['screen']['children'])['portrait_height'] = $scope.element_portrait_height;
          find($scope.element_id, nodeTree['screen']['children'])['landscape_width'] = $scope.element_landscape_width;
          find($scope.element_id, nodeTree['screen']['children'])['landscape_height'] = $scope.element_landscape_height;

          find($scope.element_id, nodeTree['screen']['children'])['portraitX'] = $scope.element_portrait_x;
          find($scope.element_id, nodeTree['screen']['children'])['portraitY'] = $scope.element_portrait_y;
          find($scope.element_id, nodeTree['screen']['children'])['landscapeX'] = $scope.element_landscape_x;
          find($scope.element_id, nodeTree['screen']['children'])['landscapeY'] = $scope.element_landscape_y;

          /* show Components tab content */
          $('.components').addClass('active');

          /* blur other highlighted elements */
          $('#screen').find('.resizable').removeClass('selected');

          /* highlight selected element */
          $(element).parents('.resizable').addClass('selected');

          /* delete component */
          $('.delete').addClass('visible');

          /* show custom properties */
          console.log("ELEMENT COMPONENET.........."+$scope.element_type);
          switch ($scope.element_type) {
            case 'table':
              $scope.element_rows = find($scope.element_id, nodeTree['screen']['children'])['rows'];
              $scope.element_columns = find($scope.element_id, nodeTree['screen']['children'])['columns'];

              /* select first column */
              $scope.element_column = $scope.element_columns[0];
              
              /* show grid panel */
              $('#grid-panel').removeClass('hidden');

              /* hide image upload */
              $('#form_camera_prop').addClass('hidden');

              /* hide tab panel */
              $('#tab-panel').addClass('hidden');

              /* hide card panel */
              $('#card-panel').addClass('hidden');

              /* hide list panel */
              $('#list-panel').addClass('hidden');

              /* hide radio panel */
              $('#radio-panel').addClass('hidden');

              /* hide image upload  */
              $('#form_image_upload').addClass('hidden');

              /* hide slider  */
              $('#form_slider_prop').addClass('hidden');
              
              /* hide map prop */
              $('#map-default').addClass('hidden');
              
              break;
            /* Boopathi */
            case 'card':
              $scope.element_card_header = find($scope.element_id, nodeTree['screen']['children'])['header'];
              $scope.element_card_footer = find($scope.element_id, nodeTree['screen']['children'])['footer'];

              /* show card panel */
              $('#card-panel').removeClass('hidden');

              /* hide image upload */
              $('#form_camera_prop').addClass('hidden');

              /* show tab panel */
              $('#tab-panel').addClass('hidden');

              /* hide grid panel */
              $('#grid-panel').addClass('hidden');

              /* hide list panel */
              $('#list-panel').addClass('hidden');

              /* hide radio panel */
              $('#radio-panel').addClass('hidden');

              /* hide image upload  */
              $('#form_image_upload').addClass('hidden');

              /* hide slider  */
              $('#form_slider_prop').addClass('hidden');
              
              /* hide map prop */
              $('#map-default').addClass('hidden');
              
              break;
            /* Boopathi */
            case 'tab':
              $scope.element_tabs = find($scope.element_id, nodeTree['screen']['children'])['children'];

              /* select first tab */
              if (!$scope.element_tab) {
                $scope.element_tab = $scope.element_tabs[Object.keys($scope.element_tabs)[0]];
              }

              /* hide image upload */
              $('#form_camera_prop').addClass('hidden');

              /* show tab panel */
              $('#tab-panel').removeClass('hidden');

              /* hide grid panel */
              $('#grid-panel').addClass('hidden');

              /* show accordion panel */
              $('#card-panel').addClass('hidden');

              /* hide list panel */
              $('#list-panel').addClass('hidden');

              /* hide radio panel */
              $('#radio-panel').addClass('hidden');

              /* hide image upload  */
              $('#form_image_upload').addClass('hidden');

              /* hide slider  */
              $('#form_slider_prop').addClass('hidden');
              
              /* hide map prop */
              $('#map-default').addClass('hidden');
              
              break;
            /* Boopathi */
            case 'list':
              $scope.element_list = find($scope.element_id, nodeTree['screen']['children'])['columns'];

              /* select first tab */
              if (!$scope.element_list_item) {
                $scope.element_list_item = $scope.element_list[0];
              }

              /* show list panel */
              $('#list-panel').removeClass('hidden');

              /* hide image upload */
              $('#form_camera_prop').addClass('hidden');

              /* hide grid panel */
              $('#grid-panel').addClass('hidden');

              /* show accordion panel */
              $('#card-panel').addClass('hidden');

              /* hide tab panel */
              $('#tab-panel').addClass('hidden');

              /* hide radio panel */
              $('#radio-panel').addClass('hidden');

              /* hide image upload  */
              $('#form_image_upload').addClass('hidden');

              /* hide slider  */
              $('#form_slider_prop').addClass('hidden');
              
              /* hide map prop */
              $('#map-default').addClass('hidden');
              
              break;
            /* Boopathi */
            case 'radio':
              $scope.element_radio_buttons = find($scope.element_id, nodeTree['screen']['children'])['columns'];

              /* select first tab */
              if (!$scope.element_radio_button) {
                $scope.element_radio_button = $scope.element_radio_buttons[0];
              }

              /* show radio panel */
              $('#radio-panel').removeClass('hidden');

              /* hide image upload */
              $('#form_camera_prop').addClass('hidden');

              /* hide list panel */
              $('#list-panel').addClass('hidden');

              /* hide grid panel */
              $('#grid-panel').addClass('hidden');

              /* show accordion panel */
              $('#card-panel').addClass('hidden');

              /* hide list panel */
              $('#tab-panel').addClass('hidden');

              /* hide image upload  */
              $('#form_image_upload').addClass('hidden');
              
              /* hide slider  */
              $('#form_slider_prop').addClass('hidden');

              /* hide map prop */
              $('#map-default').addClass('hidden');
              
              break;
            /* Boopathi */
            case 'image':
              /* show image upload */
              $('#form_image_upload').removeClass('hidden');

              /* hide image upload */
              $('#form_camera_prop').addClass('hidden');

              /* show radio panel */
              $('#radio-panel').addClass('hidden');

              /* hide list panel */
              $('#list-panel').addClass('hidden');

              /* hide grid panel */
              $('#grid-panel').addClass('hidden');

              /* show accordion panel */
              $('#card-panel').addClass('hidden');

              /* hide list panel */
              $('#tab-panel').addClass('hidden');
              
              /* hide slider  */
              $('#form_slider_prop').addClass('hidden');
              
              /* hide map prop */
              $('#map-default').addClass('hidden');

              break;
            
            case 'camera':
            	
                /* show image upload */
                $('#form_camera_prop').removeClass('hidden');

                /* show radio panel */
                $('#radio-panel').addClass('hidden');

                /* hide list panel */
                $('#list-panel').addClass('hidden');

                /* hide grid panel */
                $('#grid-panel').addClass('hidden');

                /* show accordion panel */
                $('#card-panel').addClass('hidden');

                /* hide list panel */
                $('#tab-panel').addClass('hidden');

                /* hide image upload  */
                $('#form_image_upload').addClass('hidden');
                
                /* hide slider  */
                $('#form_slider_prop').addClass('hidden');
                
                /* hide map prop */
                $('#map-default').addClass('hidden');
                
                break;

            case 'range':
            	
            	/* show slider prop */
                $('#form_slider_prop').removeClass('hidden');

                /* show image upload */
                $('#form_camera_prop').addClass('hidden');

                /* show radio panel */
                $('#radio-panel').addClass('hidden');

                /* hide list panel */
                $('#list-panel').addClass('hidden');

                /* hide grid panel */
                $('#grid-panel').addClass('hidden');

                /* show accordion panel */
                $('#card-panel').addClass('hidden');

                /* hide list panel */
                $('#tab-panel').addClass('hidden');

                /* hide image upload  */
                $('#form_image_upload').addClass('hidden');
                
                /* hide map prop */
                $('#map-default').addClass('hidden');
                
                break;
            
            case 'map':
            	            	
            	/* show map prop */
                $('#map-default').removeClass('hidden');            	
            	
            	/* show slider prop */
                $('#form_slider_prop').addClass('hidden');

                /* show image upload */
                $('#form_camera_prop').addClass('hidden');

                /* show radio panel */
                $('#radio-panel').addClass('hidden');

                /* hide list panel */
                $('#list-panel').addClass('hidden');

                /* hide grid panel */
                $('#grid-panel').addClass('hidden');

                /* show accordion panel */
                $('#card-panel').addClass('hidden');

                /* hide list panel */
                $('#tab-panel').addClass('hidden');

                /* hide image upload  */
                $('#form_image_upload').addClass('hidden');
                
                break;

            default:
              /* hide grid panel */
              $('#grid-panel').addClass('hidden');

              /* hide table-data */
              $('.table-data').removeClass('visible');

              /* hide card panel */
              $('#card-panel').addClass('hidden');

              /* hide tab panel */
              $('#tab-panel').addClass('hidden');

              /* hide radio panel */
              $('#radio-panel').addClass('hidden');

              /* hide list panel */
              $('#list-panel').addClass('hidden');

              /* hide image upload  */
              $('#form_image_upload').addClass('hidden');
              
              /* hide image upload */
              $('#form_camera_prop').addClass('hidden');
              
              /*hide slider prop*/
              $('#form_slider_prop').addClass('hidden');
              
              /* hide map prop */
              $('#map-default').addClass('hidden');

          }
          	console.log("here------------------------------------------->  "+ $scope.element_type);
          /* Data Bindings */
          switch ($scope.element_type) {
            case 'label':
            case 'checkbox':
            case 'radio':
            case 'toggle':
            case 'input':
            case 'date':
            case 'list':
            case 'select':
            case 'rich-text-editor':
            case 'camera':
            case 'recorder' :
            case 'video' :	
            case 'table':
            case 'map':
              /* show Data Bindings tab content */
              $('.data_bindings').addClass('active');
              $scope.getNounData();

              break;
            default:
              /* hide Data Bindings tab content */
              $('.data_bindings').removeClass('active');
          }

          /* Verb Bindings */
          $('.verb_bindings').addClass('active');

          $scope.events = {
            0: 'CLICK',
            1: 'KEY_DOWN'
          };

          /* TODO: implement this for multiple event_verb combos */
          var event_verb_combo = find($scope.element_id, nodeTree['screen']['children'])['event_verb_combo'];
          event_verb_combo = event_verb_combo.split(';', 1)[0].split(',');
          var event = event_verb_combo[0];
          var verb = event_verb_combo[1];
          /* show selected event */
          $scope.event = (event) ? event : undefined;
          /* show selected event */
          $scope.verb = (verb) ? verb : undefined;

          var base_verbs= $scope.verbs;
          //console.log("base_verbsbase_verbs"+angular.toJson(base_verbs[0].name)+base_verbs[0].id);
          if($scope.verb!=undefined){
	          for(var i=0;i<base_verbs.length;i++){
	        	  if(base_verbs[i].name==verb){
	        		  $scope.verb = {id: base_verbs[i].id};
	        	  }
	          }
          }
          /* show BPMN diagram */
          switch ($scope.verb) {
            case 'CREATE':
              $('.bpmn-diagram').addClass('active');

              break;
            default:
              $('.bpmn-diagram').removeClass('active');
          }

          /* Boopathi */
          /* show verb target screen */
          switch ($scope.verb) {
            case 'GpSearch':
              $scope.extra_verb_info = parseInt(find($scope.element_id, nodeTree['screen']['children'])['extra_verb_info']);
              $('#verb_info').removeClass('hidden');

              break;
            default:
              $('#verb_info').addClass('hidden');
          }
        });
      }
    };

    $scope.setElementLabel = function () {
      /* set element label */
      find($scope.element_id, nodeTree['screen']['children'])['label'] = $scope.element_label;
      var $element_id = $('#' + $scope.element_id);

      /* show element label */
      switch ($scope.element_type) {
        case 'label':
          $element_id.find('label').text($scope.element_label);

          break;
        case 'button':
          $element_id.find('button').text($scope.element_label);

          break;
        case 'camera':
          $element_id.find('button > span').text($scope.element_label);

          break;
        case 'recorder':
            $element_id.find('button > span').text($scope.element_label);

            break; 
        case 'video':
            $element_id.find('button > span').text($scope.element_label);

            break;     
        /* Boopathi */
        case 'checkbox':
          $('#' + $scope.element_id + ' label').find('span').text($scope.element_label);

          break;
        case 'radio':
          $element_id.find('.text').text($scope.element_label);

          break;
        case 'panel':
          $element_id.find('.panel-title').text($scope.element_label);
          break;
          
        case 'map':
            $element_id.find('.label').text($scope.element_label);
            break;
      }

      /* resize element if label is big */
      new Resizable($element_id);
    };

    /* Boopathi */
    /* set card header and footer label */
    $scope.setElementCardHeader = function () {
      find($scope.element_id, nodeTree['screen']['children'])['header'] = $scope.element_card_header;
      $('#' + $scope.element_id).find('div[data-role="card-header"]').text($scope.element_card_header);
    };

    $scope.setElementCardFooter = function () {
      find($scope.element_id, nodeTree['screen']['children'])['footer'] = $scope.element_card_footer;
      $('#' + $scope.element_id).find('div[data-role="card-footer"]').text($scope.element_card_footer);
    };

    /* Boopathi */
    /* ======= List ======== */
    /* set list item label */
    $scope.setListItemLabel = function () {
      if ($scope.element_list_item) {
        var index = find($scope.element_id, nodeTree['screen']['children'])['columns'].indexOf($scope.element_list_item);

        /* change selected item in the list */
        $('#' + $scope.element_id).find('li').eq(index).text($scope.element_list_item['label']);
        /* change selected item label in the nodeTree */
        find($scope.element_id, nodeTree['screen']['children'])['columns']
          [index]['label'] = $scope.element_list_item['label'];
      }
    };

    /* set list item name */
    $scope.setListItemName = function () {
      if ($scope.element_list_item) {
        var index = find($scope.element_id, nodeTree['screen']['children'])['columns'].indexOf($scope.element_list_item);
        /* update nodetree */
        find($scope.element_id, nodeTree['screen']['children'])
          ['columns'][index]['name'] = $scope.element_list_item['name'];
      }
    };

    /* add list item */
    $scope.addListItem = function () {

      /* add list item to the nodeTree */
      find($scope.element_id, nodeTree['screen']['children'])['columns'].push({
        id: 0,
        type: 'list_item',
        name: 'list_' + $scope.widgetIndex[++$scope.widgetCount], /* Boopathi */
        description: '',
        label: 'Item ' + (Object.keys($scope.element_list).length + 1),
        notes: '',
        parent_id: 0,
        parent_name: '',
        screen_id: nodeTree['screen']['id'],
        number_of_children: 0,
        supports_label: '',
        ui_technology: '',
        is_container: false,
        data_binding_context: 'not_bound',
        verb_binding_context: 'not_bound',
        events: '',
        event_verb_combo: '',
        verb_target: 'primary_noun',
        css_class: '',
        width: 0,
        height: 0
      });

      /* show added item */
      $scope.element_list = find($scope.element_id, nodeTree['screen']['children'])['columns'];

      /* select added item */
      $scope.element_list_item = $scope.element_list[$scope.element_list.length - 1];

      /* add item to the list view */
      $('#' + $scope.element_id + ' ul').append('<li class="ionic item">Item ' + Object.keys($scope.element_list).length + '</li>');

      /* resize element */
      new Resizable($('#' + $scope.element_id));
    };

    /* remove list item */
    $scope.removeListItem = function () {
      if ($scope.element_list_item && $scope.element_list.length !== 1) {
        var index = find($scope.element_id, nodeTree['screen']['children'])['columns'].indexOf($scope.element_list_item);

        /* remove item from the nodeTree */
        find($scope.element_id, nodeTree['screen']['children'])['columns'].splice(index, 1);

        /* remove item from the list */
        $('#' + $scope.element_id + ' li:nth-child(' + (index + 1) + ')').remove();

        $scope.element_list = find($scope.element_id, nodeTree['screen']['children'])['columns'];

        /* select previous or next item from the list */
        $scope.element_list_item = (index < 1) ? $scope.element_list[index] : $scope.element_list[index - 1];
      }

      /* resize element */
      new Resizable($('#' + $scope.element_id));
    };

    $scope.moveListItemUp = function () {
      if ($scope.element_list_item) {
        var list = find($scope.element_id, nodeTree['screen']['children'])['columns'];
        var index = list.indexOf($scope.element_list_item);

        if (index > 0) {
          /* swap items in the nodeTree */
          var temp = list[index];
          list[index] = list[index - 1];
          list[index - 1] = temp;

          /* swap items in the list */
          var li1 = $('#' + $scope.element_id + ' li:eq(' + (index - 1) + ')');
          var li2 = $('#' + $scope.element_id + ' li:eq(' + index + ')');
          li1.detach().insertAfter(li2);
        }
      }
    };

    $scope.moveListItemDown = function () {
      if ($scope.element_list_item) {
        var list = find($scope.element_id, nodeTree['screen']['children'])['columns'];
        var index = list.indexOf($scope.element_list_item);

        if (index < list.length - 1) {
          /* swap items in the nodeTree */
          var temp = list[index];
          list[index] = list[index + 1];
          list[index + 1] = temp;

          /* swap items in the list */
          var li1 = $('#' + $scope.element_id + ' li:eq(' + index + ')');
          var li2 = $('#' + $scope.element_id + ' li:eq(' + (index + 1) + ')');
          li1.detach().insertAfter(li2);
        }
      }
    };

    /* Boopathi */
    /* ======= radio ======== */
    /* set radio button label */
    $scope.setRadioButtonLabel = function () {
      if ($scope.element_radio_buttons) {
        var index = find($scope.element_id, nodeTree['screen']['children'])['columns'].indexOf($scope.element_radio_button);
        /* change radio button label in the radio group */
        $('#' + $scope.element_id).find('label').eq(index).find('div').text($scope.element_radio_button['label']);

        /* change selected radio button label in the nodeTree */
        find($scope.element_id, nodeTree['screen']['children'])['columns']
          [index]['label'] = $scope.element_radio_button['label'];
      }
    };

    /* set radio button name */
    $scope.setRadioButtonName = function () {
      if ($scope.element_radio_button) {
        var index = find($scope.element_id, nodeTree['screen']['children'])['columns'].indexOf($scope.element_radio_button);
        /* update nodetree */
        find($scope.element_id, nodeTree['screen']['children'])['columns'][index]['name'] = $scope.element_radio_button['name'];
      }
    };

    /* add radio button */
    $scope.addRadioButton = function () {

      /* add radio button to the nodeTree */
      find($scope.element_id, nodeTree['screen']['children'])['columns'].push({
        id: 0,
        type: 'radio',
        name: 'Radio_' + $scope.widgetIndex[++$scope.widgetCount], /* Boopathi */
        description: '',
        label: 'radio ' + (Object.keys($scope.element_radio_buttons).length + 1),
        notes: '',
        parent_id: 0,
        parent_name: '',
        screen_id: nodeTree['screen']['id'],
        number_of_children: 0,
        supports_label: '',
        ui_technology: '',
        is_container: true,
        data_binding_context: 'not_bound',
        verb_binding_context: 'not_bound',
        events: '',
        event_verb_combo: '',
        verb_target: 'primary_noun',
        css_class: '',
        width: 0,
        height: 0
      });

      /* show added radio button */
      $scope.element_radio_buttons = find($scope.element_id, nodeTree['screen']['children'])['columns'];

      /* select added radio button */
      $scope.element_radio_button = $scope.element_radio_buttons[$scope.element_radio_buttons.length - 1];

      var node = '<label class="ionic item item-radio">\
            				<input type="radio" name="group" checked="" value="on">\
            				<div class="ionic item-content">Radio ' + Object.keys($scope.element_radio_buttons).length + '</div>\
            				<i class="ionic radio-icon ion-ios-circle-filled"></i>\
            			</label>';

      /* add radio button to the radio group*/
      $('#' + $scope.element_id).find('div[data-role="radio"]').append(node);

      /* resize element */
      new Resizable($('#' + $scope.element_id));
    };

    /* remove radio button */
    $scope.removeRadioButton = function () {
      if ($scope.element_radio_button && $scope.element_radio_buttons.length !== 1) {
        var index = find($scope.element_id, nodeTree['screen']['children'])['columns'].indexOf($scope.element_radio_button);

        /* remove radio button from the nodeTree */
        find($scope.element_id, nodeTree['screen']['children'])['columns'].splice(index, 1);

        /* remove radio button from the radio group */
        $('#' + $scope.element_id + ' label:nth-child(' + (index + 1) + ')').remove();

        $scope.element_radio_buttons = find($scope.element_id, nodeTree['screen']['children'])['columns'];

        /* select previous or next radio button from the radio group */
        $scope.element_radio_button = (index < 1) ? $scope.element_radio_buttons[index] : $scope.element_radio_buttons[index - 1];

        /* resize element */
        new Resizable($('#' + $scope.element_id));
      }
    };

    $scope.moveRadioButtonUp = function () {
      if ($scope.element_radio_button) {
        var radio = find($scope.element_id, nodeTree['screen']['children'])['columns'];
        var index = radio.indexOf($scope.element_radio_button);

        if (index > 0) {
          /* swap radio button in the nodeTree */
          var temp = radio[index];
          radio[index] = radio[index - 1];
          radio[index - 1] = temp;

          /* swap radio button in the list */
          var radio1 = $('#' + $scope.element_id + ' label:eq(' + (index - 1) + ')');
          var radio2 = $('#' + $scope.element_id + ' label:eq(' + index + ')');
          radio1.detach().insertAfter(radio2);
        }
      }
    };

    $scope.moveRadioButtonDown = function () {
      if ($scope.element_radio_button) {
        var radio = find($scope.element_id, nodeTree['screen']['children'])['columns'];
        var index = radio.indexOf($scope.element_radio_button);

        if (index < radio.length - 1) {
          /* swap radio button in the nodeTree */
          var temp = radio[index];
          radio[index] = radio[index + 1];
          radio[index + 1] = temp;

          /* swap radio button in the list */
          var radio1 = $('#' + $scope.element_id + ' label:eq(' + index + ')');
          var radio2 = $('#' + $scope.element_id + ' label:eq(' + (index + 1) + ')');
          radio1.detach().insertAfter(radio2);
        }
      }
    };

    $scope.clearElementInfo = function () {
      $scope.$apply(function () {
        /* Components */
        $('.components').removeClass('active');

        $scope.element_name = undefined;
        $scope.element_label = undefined;
        $scope.element_description = undefined;
        /* TODO: clear element_notes */

        $scope.element_id = undefined;
        $scope.element_portrait_width = undefined;
        $scope.element_portrait_height = undefined;
        $scope.element_landscape_width = undefined;
        $scope.element_landscape_height = undefined;
        $scope.element_portrait_x = undefined;
        $scope.element_portrait_y = undefined;
        $scope.element_landscape_x = undefined;
        $scope.element_landscape_y = undefined;
        $scope.element_rows = undefined;
        $scope.element_columns = undefined;
        $scope.element_column = undefined;

        $scope.element_type = 'screen';

        /* Data Bindings */
        $('.data_bindings').removeClass('active');

        $scope.noun_type = undefined;

        /* clear element primary noun info */
        $scope.pn_attribute = undefined;

        /* clear element secondary nouns info */
        $scope.secondary_noun = undefined;
        $scope.sn_attribute = undefined;

        /* TODO: test verb bindings */
        /* Verb Bindings */
        $('.verb_bindings').addClass('active');

        $scope.events = {
          0: 'ON_LOAD'
        };

        var event_verb = nodeTree['screen']['event_verb_combo'];
        if (event_verb) {
          event_verb = event_verb.split(';', 1)[0].split(',');
          /* show selected event */
          $scope.event = (event_verb[0]) ? event_verb[0] : undefined;
          /* show selected event */
          $scope.verb = (event_verb[1]) ? event_verb[1] : undefined;
        } else {
          /* reset the event */
          $scope.event = undefined;
          /* reset the verb */
          $scope.verb = undefined;
        }

        /* reset verb target screen */
        $scope.target_screen = undefined;

        $('#verb_info').addClass('hidden');
      });
    };

    /* Boopathi */
    /* === component Portrait width === */
    $scope.changePortraitWidth = function () {
      switch ($scope.element_type) {
        case 'vrule':

          break;
        default:
          /* change width of element */
          $('#' + $scope.element_id).width($scope.element_portrait_width);
          /* update nodeTree */
          find($scope.element_id, nodeTree['screen']['children'])['portrait_width'] = $scope.element_portrait_width;
          
          checkOverlap();
      }
    };

    /* === component Portrait height === */
    $scope.changePortraitHeight = function () {
      switch ($scope.element_type) {
        case 'hrule':

          break;
        case 'accordion':

          break;
        default:
          /* change height of element */
          $('#' + $scope.element_id).height($scope.element_portrait_height);
          /* update nodeTree */
          find($scope.element_id, nodeTree['screen']['children'])['portrait_height'] = $scope.element_portrait_height;
          
          checkOverlap();
      }
    };

    /* === component Landscape width === */
    $scope.changeLandscapeWidth = function () {
      switch ($scope.element_type) {
        case 'vrule':

          break;
        default:
          /* change width of element */
          $('#' + $scope.element_id).width($scope.element_landscape_width);
          /* update nodeTree */
          find($scope.element_id, nodeTree['screen']['children'])['portrait_width'] = $scope.element_landscape_width;
      }
    };

    /* === component Landscape height === */
    $scope.changeLandscapeHeight = function () {
      switch ($scope.element_type) {
        case 'hrule':

          break;
        case 'accordion':

          break;
        default:
          /* change height of element */
          $('#' + $scope.element_id).height($scope.element_landscape_height);
          /* update nodeTree */
          find($scope.element_id, nodeTree['screen']['children'])['portrait_height'] = $scope.element_landscape_height;
      }
    };

    /* === component Portrait Top position === */
    $scope.changePortraitTop = function () {
      /* check element is ouside of screen vertical */

      var $element_id = $('#' + $scope.element_id);
      var $screen = $('#screen');
      var offSetY;

      if ((Number($element_id.height()) < 0) || (($screen.height() - $element_id.height()) < (~~$scope.element_portrait_y))) {
        console.log('outside of screen');
      } else {
        /* update UI */
       /* $screen.offset({
          top: (~~$screen.offset().top) + (~~$scope.element_portrait_y)
        });*/

        $element_id.offset({
        	top: (~~$screen.offset().top) + (~~$scope.element_portrait_y)
          });
        /* iterate through all the children to get parent */
        var object = nodeTree['screen']['children'] ? nodeTree['screen']['children'] : {};
        var property = $scope.element_id;
        for (var firstLevel in object) {
            if (object.hasOwnProperty(firstLevel) && object[property]!= undefined) {
                /* update nodeTree */
            	if(firstLevel == property)
            		find($scope.element_id, nodeTree['screen']['children'])['portraitY'] = ~~$scope.element_portrait_y;
                
                //check for overlapp
                var topsec = find($scope.element_id, nodeTree['screen']['children'])['portraitY'];
                var leftsec = find($scope.element_id, nodeTree['screen']['children'])['portraitX'];
                var widthsec = find($scope.element_id, nodeTree['screen']['children'])['portrait_width'];
                var heightsec = find($scope.element_id, nodeTree['screen']['children'])['portrait_height'];
                
            	var topfirst = object[firstLevel].portraitY;
                var leftfirst = object[firstLevel].portraitX;
                var widthfirst = object[firstLevel].portrait_width;
                var heightfirst = object[firstLevel].portrait_height;
               
                if(firstLevel != $scope.element_id){
                	if(isOverlap(topfirst ,topsec ,leftfirst ,leftsec ,widthfirst ,eval(widthsec) ,heightfirst ,eval(heightsec) )) //is overlapping
                	{
                		$('#' + $scope.element_id).css({
                	          border: "1px solid red"
                	        });
                		$('#' + firstLevel).css({
              	          border: "1px solid red"
              	        });
                		dragscreenobjectid = $scope.element_id;
                		levelscreenid = firstLevel;
                		overlappingscreen = true;
                		$scope.overlapping = true;
                		$("<div title='Warning'>The components are too close,please give more space before proceeding further !</div>").dialog();
                		break;
                	}else{
                		$('#' + $scope.element_id).css({
              	          border: "none"
              	        });
              		$('#' + firstLevel).css({
            	          border: "none"
            	        });
                	}
                }
              } else {
            	  // sec level
                if (object[firstLevel].hasOwnProperty('children')) {
                  for (var secondLevel in object[firstLevel]['children']) {
                    if (object[firstLevel]['children'].hasOwnProperty(secondLevel) && object[firstLevel]['children'][property]!= undefined) {
                    if(secondLevel == property){
                      /* update nodeTree */
                  	  var offSetY = $('#' + $scope.element_id).offset().top - $('#' + object[firstLevel]['children'][property]['parent_id']).offset().top;
                        find($scope.element_id, nodeTree['screen']['children'])['portraitY'] = ~~$scope.element_portrait_y;
                        find($scope.element_id, nodeTree['screen']['children'])['portraitOffsetY'] = Math.abs(offSetY);
                    }
                      
                    //check for overlapp
                      var topsec = find($scope.element_id, nodeTree['screen']['children'])['portraitY'];
                      var leftsec = find($scope.element_id, nodeTree['screen']['children'])['portraitX'];
                      var widthsec = find($scope.element_id, nodeTree['screen']['children'])['portrait_width'];
                      var heightsec = find($scope.element_id, nodeTree['screen']['children'])['portrait_height'];
                      
                  	var topfirst = object[firstLevel]['children'][secondLevel].portraitY;
                      var leftfirst = object[firstLevel]['children'][secondLevel].portraitX;
                      var widthfirst = object[firstLevel]['children'][secondLevel].portrait_width;
                      var heightfirst = object[firstLevel]['children'][secondLevel].portrait_height;
                     
                      if(secondLevel != $scope.element_id){
                      	if(isOverlap(topfirst ,topsec ,leftfirst ,leftsec ,widthfirst ,eval(widthsec) ,heightfirst ,eval(heightsec) )) //is overlapping
                      	{
                      		$('#' + $scope.element_id).css({
                      	          border: "1px solid red"
                      	        });
                      		$('#' + secondLevel).css({
                    	          border: "1px solid red"
                    	        });
                      		overlappingsingle = true;
                            dragsingleobjectid = $scope.element_id;
                            levelsingleid = secondLevel;
                            $scope.overlapping = true;
                      		$("<div title='Warning'>The components are too close,please give more space before proceeding further !</div>").dialog();
                      		break;
                      	}else{
                      		$('#' + $scope.element_id).css({
                    	          border: "none"
                    	        });
                      		$('#' + secondLevel).css({
                  	          border: "none"
                  	        });
                      	  overlappingsingle = false;
                            dragsingleobjectid = 0;
                            levelsingleid = 0;
                            $scope.overlapping = false;
                      	}
                      }
                    } else {
                      if (object[firstLevel]['children'][secondLevel].hasOwnProperty('children')) {
                        for (var thirdLevel in object[firstLevel]['children'][secondLevel]['children']) {
                          /* update nodeTree */
                          if (object[firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                        	  if(thirdLevel == property){
                        		var offSetY = $('#' + $scope.element_id).offset().top -
                              $('#' + find(object[firstLevel]['children'][secondLevel]['children'][property]['parent_id'], object)['parent_id']).offset().top;
                        		find($scope.element_id, nodeTree['screen']['children'])['portraitOffsetY'] = Math.abs(offSetY);
                        		find($scope.element_id, nodeTree['screen']['children'])['portraitY'] = ~~$scope.element_portrait_y;
                        	  }
                        	  //check overlap for third level control
                        	  var topsec = find($scope.element_id, nodeTree['screen']['children'][firstLevel]['children'])['portraitY'];
                              var leftsec = find($scope.element_id, nodeTree['screen']['children'][firstLevel]['children'])['portraitX'];
                              var widthsec = find($scope.element_id, nodeTree['screen']['children'][firstLevel]['children'])['portrait_width'];
                              var heightsec = find($scope.element_id, nodeTree['screen']['children'][firstLevel]['children'])['portrait_height'];
                              
                          	  var topfirst = object[firstLevel]['children'][secondLevel]['children'][thirdLevel].portraitY;
                              var leftfirst = object[firstLevel]['children'][secondLevel]['children'][thirdLevel].portraitX;
                              var widthfirst = object[firstLevel]['children'][secondLevel]['children'][thirdLevel].portrait_width;
                              var heightfirst = object[firstLevel]['children'][secondLevel]['children'][thirdLevel].portrait_height;
                             
                              if(thirdLevel != $scope.element_id){
                              	if(isOverlap(topfirst ,topsec ,leftfirst ,leftsec ,widthfirst ,eval(widthsec) ,heightfirst ,eval(heightsec) )) //is overlapping
                              	{
                              		$('#' + $scope.element_id).css({
                              	          border: "1px solid red"
                              	        });
                              		$('#' + thirdLevel).css({
                            	          border: "1px solid red"
                            	        });
                              		overlappingmulti = true;
                                    dragmultiobjectid = $scope.element_id;
                                    levelmultiid = secondLevel;
                                    $scope.overlapping = true;
                              		$("<div title='Warning'>The components are too close,please give more space before proceeding further !</div>").dialog();
                              		break;
                              	}else{
                              		$('#' + $scope.element_id).css({
                            	          border: "none"
                            	        });
                              		$('#' + thirdLevel).css({
                          	          border: "none"
                          	        });
                            		overlappingmulti = false;
                                    dragmultiobjectid = 0;
                                    levelmultiid = 0;
                                    $scope.overlapping = false;
                              	}
                              }
                            
                        	  
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
      }
    };

    /* === component  Portrait left position === */
    $scope.changePortraitLeft = function () {
      /* check element is ouside of screen horizontal */

      var $element_id = $('#' + $scope.element_id);
      var $screen = $('#screen');
      var offSetX;

      if ((Number($element_id.width()) < 0) || (($screen.width() - $element_id.width()) < (~~$scope.element_portrait_x))) {
        console.log('outside of screen');
      } else {
        /* update UI */
        $element_id.offset({
          left: (~~$screen.offset().left) + (~~$scope.element_portrait_x)
        });

        /* iterate through all the children to get parent */
        var object = nodeTree['screen']['children'] ? nodeTree['screen']['children'] : {};
        var property = $scope.element_id;
        for (var firstLevel in object) {
            if (object.hasOwnProperty(firstLevel) && object[property]!= undefined) {
                /* update nodeTree */
            	if(firstLevel == property)
                find($scope.element_id, nodeTree['screen']['children'])['portraitX'] = ~~$scope.element_portrait_x;
                
                //check for overlapp
                var topsec = find($scope.element_id, nodeTree['screen']['children'])['portraitY'];
                var leftsec = find($scope.element_id, nodeTree['screen']['children'])['portraitX'];
                var widthsec = find($scope.element_id, nodeTree['screen']['children'])['portrait_width'];
                var heightsec = find($scope.element_id, nodeTree['screen']['children'])['portrait_height'];
                
            	var topfirst = object[firstLevel].portraitY;
                var leftfirst = object[firstLevel].portraitX;
                var widthfirst = object[firstLevel].portrait_width;
                var heightfirst = object[firstLevel].portrait_height;
               
                if(firstLevel != $scope.element_id){
                	if(isOverlap(topfirst ,topsec ,leftfirst ,leftsec ,widthfirst ,eval(widthsec) ,heightfirst ,eval(heightsec) )) //is overlapping
                	{
                		$('#' + $scope.element_id).css({
                	          border: "1px solid red"
                	        });
                		$('#' + firstLevel).css({
              	          border: "1px solid red"
              	        });
                		dragscreenobjectid = $scope.element_id;
                		levelscreenid = firstLevel;
                		overlappingscreen = true;
                		$scope.overlapping = true;
                		$("<div title='Warning'>The components are too close,please give more space before proceeding further !</div>").dialog();
                		break;
                	}else{
                		$('#' + $scope.element_id).css({
              	          border: "none"
              	        });
              		$('#' + firstLevel).css({
            	          border: "none"
            	        });
                	}
                }
              } else {
            	  // sec level
                if (object[firstLevel].hasOwnProperty('children')) {
                  for (var secondLevel in object[firstLevel]['children']) {
                    if (object[firstLevel]['children'].hasOwnProperty(secondLevel) && object[firstLevel]['children'][property]!= undefined) {
                    if(secondLevel == property){
                      /* update nodeTree */
                      offSetX = $element_id.offset().left - $('#' + object[firstLevel]['children'][property]['parent_id']).offset().left;
                      find($scope.element_id, nodeTree['screen']['children'])['portraitOffsetX'] = Math.abs(offSetX);
                      find($scope.element_id, nodeTree['screen']['children'])['portraitX'] = ~~$scope.element_portrait_x;
                    }
                      
                    //check for overlapp
                      var topsec = find($scope.element_id, nodeTree['screen']['children'])['portraitY'];
                      var leftsec = find($scope.element_id, nodeTree['screen']['children'])['portraitX'];
                      var widthsec = find($scope.element_id, nodeTree['screen']['children'])['portrait_width'];
                      var heightsec = find($scope.element_id, nodeTree['screen']['children'])['portrait_height'];
                      
                  	  var topfirst = object[firstLevel]['children'][secondLevel].portraitY;
                      var leftfirst = object[firstLevel]['children'][secondLevel].portraitX;
                      var widthfirst = object[firstLevel]['children'][secondLevel].portrait_width;
                      var heightfirst = object[firstLevel]['children'][secondLevel].portrait_height;
                     
                      if(secondLevel != $scope.element_id){
                      	if(isOverlap(topfirst ,topsec ,leftfirst ,leftsec ,widthfirst ,eval(widthsec) ,heightfirst ,eval(heightsec) )) //is overlapping
                      	{
                      		$('#' + $scope.element_id).css({
                      	          border: "1px solid red"
                      	        });
                      		$('#' + secondLevel).css({
                    	          border: "1px solid red"
                    	        });
                      		overlappingsingle = true;
                            dragsingleobjectid = $scope.element_id;
                            levelsingleid = secondLevel;
                            $scope.overlapping = true;
                      		$("<div title='Warning'>The components are too close,please give more space before proceeding further !</div>").dialog();
                      		break;
                      	}else{
                      		$('#' + $scope.element_id).css({
                    	          border: "none"
                    	        });
                    		$('#' + secondLevel).css({
                  	          border: "none"
                  	        });
                    		overlappingsingle = false;
                            dragsingleobjectid = 0;
                            levelsingleid = 0;
                            $scope.overlapping = false;
                      	}
                      }
                    } else {
                      if (object[firstLevel]['children'][secondLevel].hasOwnProperty('children')) {
                        for (var thirdLevel in object[firstLevel]['children'][secondLevel]['children']) {
                          /* update nodeTree */
                          if (object[firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                        	  if(thirdLevel == property){
    	                        offSetX = $element_id.offset().left -
    	                          $('#' + find(object[firstLevel]['children'][secondLevel]['children'][property]['parent_id'], object)['parent_id']).offset().left;
    	                        find($scope.element_id, nodeTree['screen']['children'])['portraitOffsetX'] = Math.abs(offSetX);
    	                        find($scope.element_id, nodeTree['screen']['children'])['portraitX'] = ~~$scope.element_portrait_x;
                        	  }
                        	  //check overlap for third level control
                        	  var topsec = find($scope.element_id, nodeTree['screen']['children'][firstLevel]['children'])['portraitY'];
                              var leftsec = find($scope.element_id, nodeTree['screen']['children'][firstLevel]['children'])['portraitX'];
                              var widthsec = find($scope.element_id, nodeTree['screen']['children'][firstLevel]['children'])['portrait_width'];
                              var heightsec = find($scope.element_id, nodeTree['screen']['children'][firstLevel]['children'])['portrait_height'];
                              
                          	  var topfirst = object[firstLevel]['children'][secondLevel]['children'][thirdLevel].portraitY;
                              var leftfirst = object[firstLevel]['children'][secondLevel]['children'][thirdLevel].portraitX;
                              var widthfirst = object[firstLevel]['children'][secondLevel]['children'][thirdLevel].portrait_width;
                              var heightfirst = object[firstLevel]['children'][secondLevel]['children'][thirdLevel].portrait_height;
                             
                              if(thirdLevel != $scope.element_id){
                              	if(isOverlap(topfirst ,topsec ,leftfirst ,leftsec ,widthfirst ,eval(widthsec) ,heightfirst ,eval(heightsec) )) //is overlapping
                              	{
                              		$('#' + $scope.element_id).css({
                              	          border: "1px solid red"
                              	        });
                              		$('#' + thirdLevel).css({
                            	          border: "1px solid red"
                            	        });
                              		overlappingmulti = true;
                                    dragmultiobjectid = $scope.element_id;
                                    levelmultiid = secondLevel;
                                    $scope.overlapping = true;
                              		$("<div title='Warning'>The components are too close,please give more space before proceeding further !</div>").dialog();
                              		break;
                              	}else{
                              		$('#' + $scope.element_id).css({
                            	          border: "none"
                            	        });
                            		$('#' + thirdLevel).css({
                          	          border: "none"
                          	        });
                            		overlappingmulti = false;
                                    dragmultiobjectid = 0;
                                    levelmultiid = 0;
                                    $scope.overlapping = false;
                              	}
                              }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
      }
    };

    /* === component landscape Top position === */
    $scope.changeLandscapeTop = function () {
      /* check element is ouside of screen vertical */

      var $element_id = $('#' + $scope.element_id);
      var $screen = $('#screen');
      var offSetY;

      if ((new Number($element_id.height()) < 0) || (($screen.height() - $element_id.height()) < (~~$scope.element_landscape_y))) {
        console.log('outside of screen');
      } else {
        /* update UI */
        $element_id.offset({
          top: (~~$screen.offset().top) + (~~$scope.element_landscape_y)
        });

        /* iterate through all the children to get parent */
        var object = nodeTree['screen']['children'] ? nodeTree['screen']['children'] : {};
        var property = $scope.element_id;
        for (var firstLevel in object) {
          if (object.hasOwnProperty(firstLevel) && firstLevel == property) {
            /* update nodeTree */
            find($scope.element_id, nodeTree['screen']['children'])['landscapeY'] = ~~$scope.element_landscape_y;
          } else {
            if (object[firstLevel].hasOwnProperty('children')) {
              for (var secondLevel in object[firstLevel]['children']) {
                if (object[firstLevel]['children'].hasOwnProperty(secondLevel) && secondLevel == property) {
                  /* update nodeTree */
                  offSetY = $element_id.offset().top - $('#' + object[firstLevel]['children'][property]['parent_id']).offset().top;
                  find($scope.element_id, nodeTree['screen']['children'])['landscapeOffsetY'] = Math.abs(offSetY);
                  find($scope.element_id, nodeTree['screen']['children'])['landscapeY'] = ~~$scope.element_landscape_y;
                } else {
                  if (object[firstLevel]['children'][secondLevel].hasOwnProperty('children')) {
                    for (var thirdLevel in object[firstLevel]['children'][secondLevel]['children']) {
                      if (object[firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel) && thirdLevel == property) {
                        /* update nodeTree */
                        offSetY = $element_id.offset().top -
                          $('#' + find(object[firstLevel]['children'][secondLevel]['children'][property]['parent_id'], object)['parent_id']).offset().top;
                        find($scope.element_id, nodeTree['screen']['children'])['landscapeOffsetY'] = Math.abs(offSetY);
                        find($scope.element_id, nodeTree['screen']['children'])['landscapeY'] = ~~$scope.element_landscape_y;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    };

    /* === component landscape left position === */
    $scope.changeLandscapeLeft = function () {
      /* check element is ouside of screen horizontal */

      var $element_id = $('#' + $scope.element_id);
      var $screen = $('#screen');
      var offSetX;

      if ((Number($element_id.width()) < 0) || (($screen.width() - $element_id.width()) < (~~$scope.element_landscape_x))) {
        console.log('outside of screen');
      } else {
        /* update UI */
        $element_id.offset({
          left: (~~$screen.offset().left) + (~~$scope.element_landscape_x)
        });

        /* iterate through all the children to get parent */
        var object = nodeTree['screen']['children'] ? nodeTree['screen']['children'] : {};
        var property = $scope.element_id;
        for (var firstLevel in object) {
          if (object.hasOwnProperty(firstLevel) && firstLevel == property) {
            /* update nodeTree */
            find($scope.element_id, nodeTree['screen']['children'])['landscapeX'] = ~~$scope.element_landscape_x;
          } else {
            if (object[firstLevel].hasOwnProperty('children')) {
              for (var secondLevel in object[firstLevel]['children']) {
                if (object[firstLevel]['children'].hasOwnProperty(secondLevel) && secondLevel == property) {
                  /* update nodeTree */
                  offSetX = $element_id.offset().left - $('#' + object[firstLevel]['children'][property]['parent_id']).offset().left;
                  find($scope.element_id, nodeTree['screen']['children'])['landscapeOffsetX'] = Math.abs(offSetX);
                  find($scope.element_id, nodeTree['screen']['children'])['landscapeX'] = ~~$scope.element_landscape_x;
                } else {
                  if (object[firstLevel]['children'][secondLevel].hasOwnProperty('children')) {
                    for (var thirdLevel in object[firstLevel]['children'][secondLevel]['children']) {
                      if (object[firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel) && thirdLevel == property) {
                        /* update nodeTree */
                        offSetX = $element_id.offset().left -
                          $('#' + find(object[firstLevel]['children'][secondLevel]['children'][property]['parent_id'], object)['parent_id']).offset().left;
                        find($scope.element_id, nodeTree['screen']['children'])['landscapeOffsetX'] = Math.abs(offSetX);
                        find($scope.element_id, nodeTree['screen']['children'])['landscapeX'] = ~~$scope.element_landscape_x;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    };

    var validateCameraVideoNounBinding = function(){
    	var firstLevel, secondLevel;

        /* iterate through all the children to check name is empty */
        var children = nodeTree['screen']['children'] ? nodeTree['screen']['children'] : {};

        for (firstLevel in children) {
          if (children.hasOwnProperty(firstLevel)) {
              if((children[firstLevel]['type'] === 'camera' ||  children[firstLevel]['type'] === 'video' ) && children[firstLevel]['data_binding_context']=== 'not_bound'){
            	  openDialog($modal, 'Noun Binding is required for ' + children[firstLevel]['type'] + '!');
              return false;
            }
            if (!$.isEmptyObject(children[firstLevel]['children'])) {
              for (secondLevel in children[firstLevel]['children']) {
                if (children[firstLevel]['children'].hasOwnProperty(secondLevel)) {
                  if (children[firstLevel]['children'][secondLevel]['type'] === 'camera' && 
                		  children[firstLevel]['children'][secondLevel]['data_binding_context'] === 'not_bound') {
                	  openDialog($modal, 'Noun Binding is required for ' + children[firstLevel]['children'][secondLevel]['type'] + '!');
                    return false;
                  }
                  if (!$.isEmptyObject(children[firstLevel]['children'][secondLevel]['children'])) {
                    for (var thirdLevel in children[firstLevel]['children'][secondLevel]['children']) {
                      if (children[firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                        if (children[firstLevel]['children'][secondLevel]['children'][thirdLevel]['type'] === 'camera' && 
                        		children[firstLevel]['children'][secondLevel]['children'][thirdLevel]['data_binding_context'] === 'not_bound') {
                          openDialog($modal, 'Noun Binding is required for ' +
                            children[firstLevel]['children'][secondLevel]['children'][thirdLevel]['type'] + '!');
                          return false;
                        }
                      }
                    }
                  }
                }
              }
            }
            }
        }
        return true;
    }
    /* validate all components names */
    var validateAllComponentsName = function () {
      var firstLevel, secondLevel;

      if (nodeTree['screen']['name'] === undefined || nodeTree['screen']['name'].length === 0) {
        openDialog($modal, 'Screen name is required !');
        return false;
      }

      /* iterate through all the children to check name is empty */
      var children = nodeTree['screen']['children'] ? nodeTree['screen']['children'] : {};

      for (firstLevel in children) {
        if (children.hasOwnProperty(firstLevel)) {
          if (children[firstLevel]['name'] === undefined || children[firstLevel]['name'].length === 0) {
            openDialog($modal, 'Name is required for ' + children[firstLevel]['type'] + '!');
            return false;
          }
          if (!$.isEmptyObject(children[firstLevel]['children'])) {
            for (secondLevel in children[firstLevel]['children']) {
              if (children[firstLevel]['children'].hasOwnProperty(secondLevel)) {
                if (children[firstLevel]['children'][secondLevel]['name'] === undefined ||
                  children[firstLevel]['children'][secondLevel]['name'] === '') {
                  openDialog($modal, 'Name is required for ' + children[firstLevel]['children'][secondLevel]['type'] + '!');
                  return false;
                }
                if (!$.isEmptyObject(children[firstLevel]['children'][secondLevel]['children'])) {
                  for (var thirdLevel in children[firstLevel]['children'][secondLevel]['children']) {
                    if (children[firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                      if (children[firstLevel]['children'][secondLevel]['children'][thirdLevel]['name'] === undefined ||
                        children[firstLevel]['children'][secondLevel]['children'][thirdLevel]['name'].length === 0) {
                        openDialog($modal, 'Name is required for ' +
                          children[firstLevel]['children'][secondLevel]['children'][thirdLevel]['type'] + '!');
                        return false;
                      }
                    }
                  }
                }
              }
            }
          }
          /* for list and grid - name validation */
          else if (children[firstLevel].hasOwnProperty('columns') && !$.isEmptyObject(children[firstLevel]['columns'])) {
            for (secondLevel in children[firstLevel]['columns']) {
              if (children[firstLevel]['columns'].hasOwnProperty(secondLevel)) {
                if (children[firstLevel]['columns'][secondLevel]['name'] === undefined
                  || children[firstLevel]['columns'][secondLevel]['name'] === '') {
                  openDialog($modal, 'Name is required for '
                    + children[firstLevel]['columns'][secondLevel]['type'] + '!');
                  return false;
                }
              }
            }
          }
        }
      }
      return true;
    };
  
	 function isOverlap(topOne,topTwo,leftOne,leftTwo,widthOne,widthTwo,heightOne,heightTwo){
	      /*var leftTop = leftTwo > leftOne && leftTwo < leftOne+widthOne 
	              && topTwo > topOne && topTwo < topOne+heightOne,
	          rightTop = leftTwo+widthTwo > leftOne && leftTwo+widthTwo < leftOne+widthOne 
	              && topTwo > topOne && topTwo < topOne+heightOne,
	          leftBottom = leftTwo > leftOne && leftTwo < leftOne+widthOne 
	              && topTwo+heightTwo > topOne && topTwo+heightTwo < topOne+heightOne,
	          rightBottom = leftTwo+widthTwo > leftOne && leftTwo+widthTwo < leftOne+widthOne 
	              && topTwo+heightTwo > topOne && topTwo+heightTwo < topOne+heightOne;
	      return leftTop || rightTop || leftBottom || rightBottom;*/
		 
		  var x1 = leftOne+5;
	      var y1 = topOne+5;
	      var h1 = heightOne+5;
	      var w1 = widthOne+5;
	      var b1 = y1 + h1;
	      var r1 = x1 + w1;
	      var x2 = leftTwo+5;
	      var y2 = topTwo+5;
	      var h2 = heightTwo+5;
	      var w2 = widthTwo+5;
	      var b2 = y2 + h2;
	      var r2 = x2 + w2;

	      if (b1 < y2 || y1 > b2 || r1 < x2 || x1 > r2) return false;
	      return true;
	  }
    
	 function checkOverlap(){
		 
		 var $element_id = $('#' + $scope.element_id);
         var $screen = $('#screen');
         
         var object = nodeTree['screen']['children'] ? nodeTree['screen']['children'] : {};
         var property = $scope.element_id;
         for (var firstLevel in object) {
           if (object.hasOwnProperty(firstLevel) && object[property]!= undefined) {
             /* update nodeTree */
         	if(firstLevel == property)
         		find($scope.element_id, nodeTree['screen']['children'])['portraitY'] = ~~$scope.element_portrait_y;
             
             //check for overlapp
             var topsec = find($scope.element_id, nodeTree['screen']['children'])['portraitY'];
             var leftsec = find($scope.element_id, nodeTree['screen']['children'])['portraitX'];
             var widthsec = find($scope.element_id, nodeTree['screen']['children'])['portrait_width'];
             var heightsec = find($scope.element_id, nodeTree['screen']['children'])['portrait_height'];
             
         	 var topfirst =   object[firstLevel].portraitY;
             var leftfirst =  object[firstLevel].portraitX;
             var widthfirst = object[firstLevel].portrait_width;
             var heightfirst = object[firstLevel].portrait_height;
            
             if(firstLevel != $scope.element_id){
             	if(isOverlap(topfirst ,topsec ,leftfirst ,leftsec ,widthfirst ,eval(widthsec) ,heightfirst ,eval(heightsec) )) //is overlapping
             	{
             		$('#' + $scope.element_id).css({
             	          border: "1px solid red"
             	        });
             		$('#' + firstLevel).css({
           	          border: "1px solid red"
           	        });
             		dragscreenobjectid = $scope.element_id;
             		levelscreenid = firstLevel;
             		overlappingscreen = true;
             		$scope.overlapping = true;
             		$("<div title='Warning'>The components are too close,please give more space before proceeding further !</div>").dialog();
             		break;
             	}else{
             		$('#' + $scope.element_id).css({
           	          border: "none"
           	        });
             		$('#' + firstLevel).css({
         	          border: "none"
         	        });
             	}
             }
           } else {
         	  // sec level
             if (object[firstLevel].hasOwnProperty('children')) {
               for (var secondLevel in object[firstLevel]['children']) {
                 if (object[firstLevel]['children'].hasOwnProperty(secondLevel) && object[firstLevel]['children'][property]!= undefined) {
                 if(secondLevel == property){
                   /* update nodeTree */
               	  var offSetY = $('#' + $scope.element_id).offset().top - $('#' + object[firstLevel]['children'][property]['parent_id']).offset().top;
                     find($scope.element_id, nodeTree['screen']['children'])['portraitY'] = ~~$scope.element_portrait_y;
                     find($scope.element_id, nodeTree['screen']['children'])['portraitOffsetY'] = Math.abs(offSetY);
                  }
                   
                 //check for overlapp
                   var topsec = find($scope.element_id, nodeTree['screen']['children'])['portraitY'];
                   var leftsec = find($scope.element_id, nodeTree['screen']['children'])['portraitX'];
                   var widthsec = find($scope.element_id, nodeTree['screen']['children'])['portrait_width'];
                   var heightsec = find($scope.element_id, nodeTree['screen']['children'])['portrait_height'];
                   
               	   var topfirst = object[firstLevel]['children'][secondLevel].portraitY;
                   var leftfirst = object[firstLevel]['children'][secondLevel].portraitX;
                   var widthfirst = object[firstLevel]['children'][secondLevel].portrait_width;
                   var heightfirst = object[firstLevel]['children'][secondLevel].portrait_height;
                  
                   if(secondLevel != $scope.element_id){
                   	if(isOverlap(topfirst ,topsec ,leftfirst ,leftsec ,widthfirst ,eval(widthsec) ,heightfirst ,eval(heightsec) )) //is overlapping
                   	{
                   		$('#' + $scope.element_id).css({
                   	          border: "1px solid red"
                   	        });
                   		$('#' + secondLevel).css({
                 	          border: "1px solid red"
                 	        });
                   		overlappingsingle = true;
                         dragsingleobjectid = $scope.element_id;
                         levelsingleid = secondLevel;
                         $scope.overlapping = true;
                   		$("<div title='Warning'>The components are too close,please give more space before proceeding further !</div>").dialog();
                   		break;
                   	}else{
                   		$('#' + $scope.element_id).css({
                 	          border: "none"
                 	        });
                 		$('#' + secondLevel).css({
               	          border: "none"
               	        });
                 		overlappingsingle = false;
                         dragsingleobjectid = 0;
                         levelsingleid = 0;
                         $scope.overlapping = false;
                   	}
                   }
                 } else {
                   if (object[firstLevel]['children'][secondLevel].hasOwnProperty('children')) {
                     for (var thirdLevel in object[firstLevel]['children'][secondLevel]['children']) {
                       /* update nodeTree */
                       if (object[firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                     	  if(thirdLevel == property){
                     		var offSetY = $('#' + $scope.element_id).offset().top -
                           $('#' + find(object[firstLevel]['children'][secondLevel]['children'][property]['parent_id'], object)['parent_id']).offset().top;
                     		find($scope.element_id, nodeTree['screen']['children'])['portraitOffsetY'] = Math.abs(offSetY);
                     		find($scope.element_id, nodeTree['screen']['children'])['portraitY'] = ~~$scope.element_portrait_y;
                     	  }
                     	  //check overlap for third level control
                     	  var topsec = find($scope.element_id, nodeTree['screen']['children'][firstLevel]['children'])['portraitY'];
                          var leftsec = find($scope.element_id, nodeTree['screen']['children'][firstLevel]['children'])['portraitX'];
                          var widthsec = find($scope.element_id, nodeTree['screen']['children'][firstLevel]['children'])['portrait_width'];
                          var heightsec = find($scope.element_id, nodeTree['screen']['children'][firstLevel]['children'])['portrait_height'];
                           
                       	  var topfirst = object[firstLevel]['children'][secondLevel]['children'][thirdLevel].portraitY;
                          var leftfirst = object[firstLevel]['children'][secondLevel]['children'][thirdLevel].portraitX;
                          var widthfirst = object[firstLevel]['children'][secondLevel]['children'][thirdLevel].portrait_width;
                          var heightfirst = object[firstLevel]['children'][secondLevel]['children'][thirdLevel].portrait_height;
                          
                           if(thirdLevel != $scope.element_id){
                           	if(isOverlap(topfirst ,topsec ,leftfirst ,leftsec ,widthfirst ,eval(widthsec) ,heightfirst ,eval(heightsec) )) //is overlapping
                           	{
                           		$('#' + $scope.element_id).css({
                           	          border: "1px solid red"
                           	        });
                           		$('#' + thirdLevel).css({
                         	          border: "1px solid red"
                         	        });
                           		overlappingmulti = true;
                                 dragmultiobjectid = $scope.element_id;
                                 levelmultiid = secondLevel;
                                 $scope.overlapping = true;
                           		$("<div title='Warning'>The components are too close,please give more space before proceeding further !</div>").dialog();
                           		break;
                           	}else{
                           		$('#' + $scope.element_id).css({
                         	          border: "none"
                         	        });
                           		$('#' + thirdLevel).css({
                       	          border: "none"
                       	        });
                         		overlappingmulti = false;
                                 dragmultiobjectid = 0;
                                 levelmultiid = 0;
                                 $scope.overlapping = false;
                           	}
                          }
                       }
                     }
                   }
                 }
               }
             }
           }
         }

	 }
	 $scope.onCancel = function(){
         nodeTree['screen']['children'] ={};
         overlappingscreen = false;
    	   dragscreenobjectid=0;
    	   levelscreenid = 0;

    	  //single container check
    	   overlappingsingle = false;
    	   dragsingleobjectid=0;
    	   levelsingleid = 0;

    	  //multi container check
    	   overlappingmulti = false;
    	   dragmultiobjectid=0;
    	   levelmultiid = 0;
    	   
         $scope.overlapping = false;
    	   $location.url($scope.back);
       }
  }]);