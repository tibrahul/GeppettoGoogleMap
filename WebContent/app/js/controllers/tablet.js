app.controller('TabletController', ['$scope', '$timeout', '$compile', '$cookieStore', '$location', '$routeParams',
  '$http', '$modal', 'ActivityInformation', 'RestURL',
  function ($scope, $timeout, $compile, $cookieStore, $location, $routeParams, $http, $modal, ActivityInfo, RestURL) {

    $scope.name = 'tablet';

    $scope.element_camera_destinationTypes =[
                                             {"label":"Camera.DestinationType.FILE_URI","value":"Camera.DestinationType.FILE_URI"},
                                             {"label":"Camera.DestinationType.DATA_URL","value":"Camera.DestinationType.DATA_URL"}
                                             ];
    common($scope, $timeout, $compile, $cookieStore, $location, $routeParams, $http, $modal, ActivityInfo, RestURL);
    
    
    /* create or update */
    switch ($routeParams['action']) {
      case 'create':
        $scope.submit = 'Create';
        $cookieStore.put('back', '/en-US/axe/tablet/create/');

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

          console.log('Creating...');
          console.log(JSON.stringify(nodeTree['screen']));
          console.log('Data of node screen role in creating...',JSON.stringify(nodeTree['screen']['role']));
                   var rolefromTabScreenCreate = nodeTree['screen']['role'];
                    //console.log('rolefromTabScreenCreate data =>',rolefromTabScreenCreate);
                    var roleForTab = empty(rolefromTabScreenCreate);
                    //console.log('roleForTab =>',roleForTab);
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
                    if(roleForTab){
                  	  nodeTree['screen']['role'] = 'ROLE_USER';
                    }
          $http.post(RestURL.baseURL + 'android/create_screen/', nodeTree['screen'])
            .success(function () {
              console.log('redirecting...');
              $location.url($scope.back);
            })
            .error(function () {
              console.log('Unable to create screen');
              /* enable create button */
              $(this).prop('disabled', false);
            });
        });

        break;
      case 'update':
        $scope.submit = 'Update';
        $cookieStore.put('back', '/en-US/axe/tablet/update/');

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

          console.log('Updating...');
          console.log(nodeTree['screen']);
          console.log(JSON.stringify(nodeTree['screen']));
          console.log('Data of node screen role in creating...',JSON.stringify(nodeTree['screen']['role']));
                    var rolefromTabScreenUpdate = nodeTree['screen']['role'];
                    //console.log('rolefromTabScreenCreate data =>',rolefromTabScreenUpdate);
                    var roleForTab = empty(rolefromTabScreenUpdate);
                    //console.log('roleForTab =>',roleForTab);
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
                    if(roleForTab){
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

      /* Boopathi */
      /* hide tab panel */
      $('#tab-panel').addClass('hidden');

      $('#accordion-panel').addClass('hidden');

      /* hide grid panel */
      $('#grid-panel').addClass('hidden');
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
          /* Boopathi */
          $scope.element_notes = find($scope.element_id, nodeTree['screen']['children'])['notes'];

          $scope.extended_attributes = find($scope.element_id, nodeTree['screen']['children'])['extended_attributes'];
     	 
     	 if($scope.extended_attributes!=null && $scope.extended_attributes!=""){
     		 $scope.extended_attributes = $scope.extended_attributes.split(',');
     		 console.log("ELEMENT.........."+$scope.extended_attributes);
     		 var quality = $scope.extended_attributes[0];
              var destType = $scope.extended_attributes[1];
              console.log("destType.........."+destType);
              /* show selected quality */
              $scope.element_camera_quality = quality;
              /* show selected dest type */
              $scope.element_camera_destinationType = destType;
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
          switch ($scope.element_type) {
            case 'table':
              $scope.element_rows = find($scope.element_id, nodeTree['screen']['children'])['rows'];
              $scope.element_columns = find($scope.element_id, nodeTree['screen']['children'])['columns'];

              /* select first column */
              $scope.element_column = $scope.element_columns[0];

              /* show grid panel */
              $('#grid-panel').removeClass('hidden');

              /* show table-data */
              $('.table-data').addClass('visible');

              /* hide tab panel */
              $('#tab-panel').addClass('hidden');

              /* hide accordion panel */
              $('#accordion-panel').addClass('hidden');

              /* hide image upload  */
              $('#form_image_upload').addClass('hidden');

              /* hide URL */
              $('#form_col_link').addClass('hidden');

              /* hide camera prop  */
              $('#form_camera_prop').addClass('hidden');

              break;
            /* Boopathi */
            case 'tab':
              $scope.element_tabs = find($scope.element_id, nodeTree['screen']['children'])['children'];

              /* select first tab */
              if (!$scope.element_tab) {
                $scope.element_tab = $scope.element_tabs[Object.keys($scope.element_tabs)[0]];
              }

              /* show tab panel */
              $('#tab-panel').removeClass('hidden');

              /* hide grid panel */
              $('#grid-panel').addClass('hidden');

              /* hide table-data */
              $('.table-data').removeClass('visible');

              /* show accordion panel */
              $('#accordion-panel').addClass('hidden');

              /* hide image upload  */
              $('#form_image_upload').addClass('hidden');

              /* hide URL */
              $('#form_col_link').addClass('hidden');

              /* hide camera prop  */
              $('#form_camera_prop').addClass('hidden');

              break;
            /* Boopathi */
            case 'accordion':
              $scope.element_accordion = find($scope.element_id, nodeTree['screen']['children'])['children'];

              /* select first panel */
              if (!$scope.element_accordion_panel) {
                $scope.element_accordion_panel = $scope.element_accordion[Object.keys($scope.element_accordion)[0]];
              }

              /* show accordion panel */
              $('#accordion-panel').removeClass('hidden');

              /* hide tab panel */
              $('#tab-panel').addClass('hidden');

              /* hide grid panel */
              $('#grid-panel').addClass('hidden');

              /* hide table-data */
              $('.table-data').removeClass('visible');

              /* hide image upload  */
              $('#form_image_upload').addClass('hidden');

              /* hide URL */
              $('#form_col_link').addClass('hidden');

              /* hide camera prop  */
              $('#form_camera_prop').addClass('hidden');

              break;
            /* Boopathi */
            case 'link':
              $scope.element_target_url = find($scope.element_id, nodeTree['screen']['children'])['target_url'];

              $('#form_col_link').removeClass('hidden');

              /* hide grid panel */
              $('#grid-panel').addClass('hidden');

              /* hide table-data */
              $('.table-data').removeClass('visible');

              /* hide tab panel */
              $('#tab-panel').addClass('hidden');

              /* hide accordion panel */
              $('#accordion-panel').addClass('hidden');

              /* hide image upload  */
              $('#form_image_upload').addClass('hidden');

              /* hide camera prop  */
              $('#form_camera_prop').addClass('hidden');

              break;
            /* Boopathi */
            case 'image':
              /* show image upload */
              $('#form_image_upload').removeClass('hidden');

              /* hide URL */
              $('#form_col_link').addClass('hidden');

              /* hide grid panel */
              $('#grid-panel').addClass('hidden');

              /* hide table-data  */
              $('.table-data').removeClass('visible');

              /* hide tab panel */
              $('#tab-panel').addClass('hidden');

              /* hide accordion panel */
              $('#accordion-panel').addClass('hidden');

              /* hide camera prop  */
              $('#form_camera_prop').addClass('hidden');

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
                break;


            default:
              /* hide grid panel */
              $('#grid-panel').addClass('hidden');

              /* hide table-data  */
              $('.table-data').removeClass('visible');

              /* hide tab panel */
              $('#tab-panel').addClass('hidden');

              /* hide accordion panel */
              $('#accordion-panel').addClass('hidden');

              /*hide link*/
              $('#form_col_link').addClass('hidden');

              /* hide image upload  */
              $('#form_image_upload').addClass('hidden');
              
              /* hide camera prop  */
              $('#form_camera_prop').addClass('hidden');

          }

          /* Data Bindings */
          switch ($scope.element_type) {
            case 'label':
            case 'checkbox':
            case 'radio':
            case 'input':
            case 'date':
            case 'select':
            case 'rich-text-editor':
            case 'table':
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
            
        case 'checkbox':
        case 'radio':
          $element_id.find('.text').text($scope.element_label);

          break;
        case 'panel':
          $element_id.find('.panel-title').text($scope.element_label);

          break;
        /* Boopathi */
        case 'link':
          $element_id.find('a').text($scope.element_label);

          break;
      }

      /* resize element if label is big */
      new Resizable($element_id);
    };

    /* Boopathi */
    $scope.setElementTargetLink = function () {
      find($scope.element_id, nodeTree['screen']['children'])['target_url'] = $scope.element_target_url;
    };

    /* === Grid ==== */

    $scope.addColumn = function () {
      /* add column to the nodeTree */
      find($scope.element_id, nodeTree['screen']['children'])['columns'].push({
        id: 0,
        type: '',
        label: 'Col ' + ($scope.element_columns.length + 1),
        description: '',
        name: 'Column_' + $scope.widgetIndex[++$scope.widgetCount],
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
        height: 0,
        portrait_width: 0,
        portrait_height: 0,
        portrait_x: 0,
        portrait_y: 0,
        portrait_offset_x: 0,
        portrait_offset_y: 0,
        landscape_width: 0,
        landscape_height: 0,
        landscape_x: 0,
        landscape_y: 0,
        landscape_offset_x: 0,
        landscape_offset_y: 0
      });

      /* show added column in the select */
      $scope.element_columns = find($scope.element_id, nodeTree['screen']['children'])['columns'];
      /* select added column */
      $scope.element_column = $scope.element_columns[$scope.element_columns.length - 1];

      /* update noun data */
      $scope.getNounData();

      /* add column to the thead of the grid */
      $('#' + $scope.element_id + ' thead tr').append('<th>' + $scope.element_column['label'] + '</th>');
      /* add column to the tbody of the grid */
      $('#' + $scope.element_id + ' tbody tr').append('<td></td>');
    };

    $scope.removeColumn = function () {
      if ($scope.element_column && $scope.element_columns.length !== 1) {
        var index = find($scope.element_id, nodeTree['screen']['children'])['columns'].indexOf($scope.element_column);

        /* put column id into deleted_widgets */
        switch ($routeParams['action']) {
          case 'update':
            nodeTree['screen']['deleted_widgets'].push($scope.element_column['id']);
            break;
        }

        /* remove column from the nodeTree */
        find($scope.element_id, nodeTree['screen']['children'])['columns'].splice(index, 1);

        /* select previous or next column */
        $scope.element_column = (index < 1) ? $scope.element_columns[index] : $scope.element_columns[index - 1];

        /* update noun data */
        $scope.getNounData();

        /* remove column from the thead of the grid */
        $('#' + $scope.element_id + ' thead tr th:nth-child(' + (index + 1) + ')').remove();
        /* remove column from the tbody of the grid */
        $('#' + $scope.element_id + ' tbody tr td:nth-child(' + (index + 1) + ')').remove();

        /* remove column from the select */
        $scope.element_columns = find($scope.element_id, nodeTree['screen']['children'])['columns'];
      }
    };

    $scope.moveColumnUp = function () {
      if ($scope.element_column) {
        var columns = find($scope.element_id, nodeTree['screen']['children'])['columns'];
        var index = columns.indexOf($scope.element_column);

        if (index > 0) {
          /* swap columns in the nodeTree */
          var temp = columns[index];
          columns[index] = columns[index - 1];
          columns[index - 1] = temp;

          /* swap columns in the grid */
          var th1 = $('#' + $scope.element_id + ' thead tr th:eq(' + (index - 1) + ')');
          var th2 = $('#' + $scope.element_id + ' thead tr th:eq(' + index + ')');
          th1.detach().insertAfter(th2);
        }
      }
    };

    $scope.moveColumnDown = function () {
      if ($scope.element_column) {
        var columns = find($scope.element_id, nodeTree['screen']['children'])['columns'];
        var index = columns.indexOf($scope.element_column);

        if (index < columns.length - 1) {
          /* swap columns in the nodeTree */
          var temp = columns[index];
          columns[index] = columns[index + 1];
          columns[index + 1] = temp;

          /* swap columns in the grid */
          var th1 = $('#' + $scope.element_id + ' thead tr th:eq(' + index + ')');
          var th2 = $('#' + $scope.element_id + ' thead tr th:eq(' + (index + 1) + ')');
          th1.detach().insertAfter(th2);
        }
      }
    };

    $scope.setColumnName = function () {
      if ($scope.element_column) {
        /* change selected column name in the nodeTree */
        var index = find($scope.element_id, nodeTree['screen']['children'])['columns'].indexOf($scope.element_column);
        find($scope.element_id, nodeTree['screen']['children'])['columns']
          [index]['name'] = $scope.element_column['name'];
      }
    };

    $scope.setColumnLabel = function () {
      if ($scope.element_column) {
        var index = find($scope.element_id, nodeTree['screen']['children'])['columns'].indexOf($scope.element_column);

        /* change selected column label in the grid */
        $('#' + $scope.element_id + ' thead th:nth-child(' + (index + 1) + ')').text($scope.element_column['label']);

        /* change selected column label in the nodeTree */
        find($scope.element_id, nodeTree['screen']['children'])['columns']
          [index]['label'] = $scope.element_column['label'];
      }
    };

    $scope.resizeColumn = function () {
      if ($scope.element_column) {
        var index = find($scope.element_id, nodeTree['screen']['children'])['columns'].indexOf($scope.element_column);

        /* change selected column width in the grid */
        $('#' + $scope.element_id + ' thead th:nth-child(' + (index + 1) + ')')
          .attr('width', $scope.element_column['width'] + '%');

        /* change selected column width in the nodeTree */
        find($scope.element_id, nodeTree['screen']['children'])['columns']
          [index]['width'] = ~~$scope.element_column['width'];
      }
    };

    $scope.addRow = function () {
      /* add row to the tbody of the grid */
      var html = '<tr>';
      for (var i = 0; i < $scope.element_columns.length; ++i) {
        html += '<td></td>';
      }
      html += '</tr>';
      $('#' + $scope.element_id + ' tbody').append(html);

      /* increment rows number in the nodeTree */
      $scope.element_rows = ++find($scope.element_id, nodeTree['screen']['children'])['rows'];
    };

    $scope.removeRow = function () {
      if ($scope.element_rows > 2) {
        /* remove row from the tbody of the grid */
        $('#' + $scope.element_id + ' tbody').find('tr').first().remove();

        /* decrement rows number in the nodeTree */
        $scope.element_rows = --find($scope.element_id, nodeTree['screen']['children'])['rows'];
      }
    };

    /* Boopathi */
    /* === Accordion ==== */

    /* set accordion panel label */
    $scope.setAccordionPanelLabel = function () {
      if ($scope.element_accordion_panel) {
        var panel = find($scope.element_id, nodeTree['screen']['children'])['children'];

        /* iterate panel and compare tab - to get selected tab */
        $.each(panel, function (key, value) {
          if (value === $scope.element_accordion_panel) {
            /* apply label */
            $('a[data-ng-href=#' + key + ']').text($scope.element_accordion_panel.label);
            /* update nodetree */
            find($scope.element_id, nodeTree['screen']['children'])['children'][key]['label'] = $scope.element_accordion_panel.label;
            return false;
          }
        });
      }
    };

    $scope.setAccordionPanelName = function () {
      if ($scope.element_accordion_panel) {
        var panel = find($scope.element_id, nodeTree['screen']['children'])['children'];

        /* iterate panel and compare tab - to get selected tab */
        $.each(panel, function (key, value) {
          if (value === $scope.element_accordion_panel) {
            /* update nodetree */
            find($scope.element_id, nodeTree['screen']['children'])['children'][key]['name'] = $scope.element_accordion_panel.name;
            return false;
          }
        });
      }
    };

    /* add panel */
    $scope.addPanel = function () {

      /* find max id and increment it for panel */
      window.id = nodeTree['screen']['children'] ? maxID(nodeTree['screen']['children']) : 0;
      window.id++;

      /* add panel to the nodeTree */
      /* TODO: test this too */
      find($scope.element_id, nodeTree['screen']['children'])['children'][id] = {
        id: 0,
        type: 'section',
        name: 'Item_' + $scope.element_id + '_' + (Math.floor(Math.random() * (999 - 99 + 1)) + 99), /* Boopathi */
        description: '',
        label: 'Item #' + (Object.keys($scope.element_accordion).length + 1),
        notes: '',
        children: {},
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
      };

      /* show added panel in the select */
      $scope.element_accordion = find($scope.element_id, nodeTree['screen']['children'])['children'];

      /* select added panel */
      $scope.element_accordion_panel = $scope.element_accordion[Object.keys($scope.element_accordion)[0]];

      var node = '<div class="panel panel-default">\
										<div data-role="panel-heading" class="panel-heading">\
											<h4 class="panel-title">\
												<a data-ng-href="#' + id + '">Item #' + (Object.keys($scope.element_accordion).length) + ' </a>\
											</h4>\
										</div>\
										<div data-role="section" class="section" id=' + id + '></div>\
									</div>';

      /* add panel to the accordion */
      $('#' + $scope.element_id + ' div[data-role="accordion"]').append(node);

      /* TODO: incremented 2 to avoid id conflicts  */
      /* increment it for next drag element */
      id = id + 2;
    };

    /* remove panel */
    $scope.removePanel = function () {
      if (Object.keys($scope.element_accordion).length > 2) {
        var panel = find($scope.element_id, nodeTree['screen']['children'])['children'];
        /* iterate panel to get selected tab */
        $.each(panel, function (key, value) {
          if (value === $scope.element_accordion_panel) {
            /* remove panel */
            $('a[data-ng-href=#' + key + ']').parents('.panel').remove();

            /* delete panel object in the nodeTree */
            delete find($scope.element_id, nodeTree['screen']['children'])['children'][key];

            return false;
          }
        });
        /* select accordion from nodeTree */
        $scope.element_accordion = find($scope.element_id, nodeTree['screen']['children'])['children'];

        /* select first panel */
        $scope.element_accordion_panel = $scope.element_accordion[Object.keys($scope.element_accordion)[0]];
      }
    };

    /* move panel up */
    $scope.moveAccordionUp = function () {
      if ($scope.element_accordion_panel) {
        var panels = find($scope.element_id, nodeTree['screen']['children'])['children'];
        var selectedPanel = 0;
        /* get selected tab */
        $.each(panels, function (key, value) {
          if (value === $scope.element_accordion_panel) {
            selectedPanel = key;
          }
        });

        var index = Object.keys(panels).indexOf(selectedPanel);
        if (index > 0) {
          /* swap elements in the nodeTree */
          var temp = panels[selectedPanel];
          panels[Object.keys(panels)[index]] = panels[Object.keys(panels)[index - 1]];
          panels[Object.keys(panels)[index - 1]] = temp;

          /* swap panels in the accordion */
          swapPanels(panels);
        }
      }
    };

    /* move panel down */
    $scope.moveAccordionDown = function () {
      if ($scope.element_accordion_panel) {
        var panels = find($scope.element_id, nodeTree['screen']['children'])['children'];
        var selectedPanel = 0;
        /* get selected tab */
        $.each(panels, function (key, value) {
          if (value === $scope.element_accordion_panel) {
            selectedPanel = key;
          }
        });

        var index = Object.keys(panels).indexOf(selectedPanel);
        if (index < Object.keys(panels).length - 1) {
          /* swap elements in the nodeTree */
          var temp = panels[selectedPanel];
          panels[Object.keys(panels)[index]] = panels[Object.keys(panels)[index + 1]];
          panels[Object.keys(panels)[index + 1]] = temp;

          /* swap panels in the accordion */
          swapPanels(panels);
        }
      }
    };

    var swapPanels = function (panels) {
      $('#' + $scope.element_id + ' div[data-role="accordion"]').find('div').remove();

      /* object key and value is not swapping, so re-creating panels */
      $.each(panels, function (key, value) {
        var node = '<div class="panel panel-default">\
											<div data-role="panel-heading" class="panel-heading">\
												<h4 class="panel-title">\
													<a data-ng-href="#' + key + '">' + value.label + ' </a>\
												</h4>\
											</div>\
											<div data-role="section" class="section" id=' + key + '></div>\
										</div>';

        /* add panel to the accordion */
        $('#' + $scope.element_id + ' div[data-role="accordion"]').append(node);
      });
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
      /* check element is outside of screen vertical */

      var $element_id = $('#' + $scope.element_id);
      var $screen = $('#screen');
      var offSetY;

      if ((Number($element_id.height()) < 0) || (($screen.height() - $element_id.height()) < (~~$scope.element_portrait_y))) {
        console.log('outside of screen');
      } else {
        /* update UI */
        $element_id.offset({
          top: (~~screen.offset().top) + (~~$scope.element_portrait_y)
        });

        /* iterate through all the children to get parent */
        var object = nodeTree['screen']['children'] ? nodeTree['screen']['children'] : {};
        var property = $scope.element_id;
        for (var firstLevel in object) {
          if (object.hasOwnProperty(firstLevel) && firstLevel == property) {
            /* update nodeTree */
            find($scope.element_id, nodeTree['screen']['children'])['portraitY'] = ~~$scope.element_portrait_y;
          } else {
            if (object[firstLevel].hasOwnProperty('children')) {
              for (var secondLevel in object[firstLevel]['children']) {
                if (object[firstLevel]['children'].hasOwnProperty(secondLevel) && secondLevel == property) {
                  /* update nodeTree */
                  offSetY = $element_id.offset().top - $('#' + object[firstLevel]['children'][property]['parent_id']).offset().top;
                  find($scope.element_id, nodeTree['screen']['children'])['portraitOffsetY'] = Math.abs(offSetY);
                  find($scope.element_id, nodeTree['screen']['children'])['portraitY'] = ~~$scope.element_portrait_y;
                } else {
                  if (object[firstLevel]['children'][secondLevel].hasOwnProperty('children')) {
                    for (var thirdLevel in object[firstLevel]['children'][secondLevel]['children']) {
                      if (object[firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel) && thirdLevel == property) {
                        /* update nodeTree */
                        offSetY = $element_id.offset().top -
                          $('#' + find(object[firstLevel]['children'][secondLevel]['children'][property]['parent_id'], object)['parent_id']).offset().top;
                        find($scope.element_id, nodeTree['screen']['children'])['portraitOffsetY'] = Math.abs(offSetY);
                        find($scope.element_id, nodeTree['screen']['children'])['portraitY'] = ~~$scope.element_portrait_y;
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
      /* check element is outside of screen horizontal */

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
          if (object.hasOwnProperty(firstLevel) && firstLevel == property) {
            /* update nodeTree */
            find($scope.element_id, nodeTree['screen']['children'])['portraitX'] = ~~$scope.element_portrait_x;
          } else {
            if (object[firstLevel].hasOwnProperty('children')) {
              for (var secondLevel in object[firstLevel]['children']) {
                if (object[firstLevel]['children'].hasOwnProperty(secondLevel) && secondLevel == property) {
                  /* update nodeTree */
                  offSetX = $element_id.offset().left - $('#' + object[firstLevel]['children'][property]['parent_id']).offset().left;
                  find($scope.element_id, nodeTree['screen']['children'])['portraitOffsetX'] = Math.abs(offSetX);
                  find($scope.element_id, nodeTree['screen']['children'])['portraitX'] = ~~$scope.element_portrait_x;
                } else {
                  if (object[firstLevel]['children'][secondLevel].hasOwnProperty('children')) {
                    for (var thirdLevel in object[firstLevel]['children'][secondLevel]['children']) {
                      if (object[firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel) && thirdLevel == property) {
                        /* update nodeTree */
                        offSetX = $element_id.offset().left -
                          $('#' + find(object[firstLevel]['children'][secondLevel]['children'][property]['parent_id'], object)['parent_id']).offset().left;
                        find($scope.element_id, nodeTree['screen']['children'])['portraitOffsetX'] = Math.abs(offSetX);
                        find($scope.element_id, nodeTree['screen']['children'])['portraitX'] = ~~$scope.element_portrait_x;
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
      /* check element is outside of screen vertical */

      var $element_id = $('#' + $scope.element_id);
      var $screen = $('#screen');
      var offSetY;

      if ((Number($element_id.height()) < 0) || (($screen.height() - $element_id.height()) < (~~$scope.element_landscape_y))) {
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
      /* check element is outside of screen horizontal */

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
  }]);