function common($scope, $timeout, $compile, $cookieStore, $location,
                $routeParams, $http, $modal, ActivityInfo, RestURL) {

  console.log('ActivityInfo ' + JSON.stringify(ActivityInfo));
  window.AxeControllerScope = $scope;

  /* history maintenance */
  $scope.back = $cookieStore.get('back');

  /* TODO: fix $cookieStore values from activity designer */
  if ($scope.back) {
    $scope.back = $scope.back.slice(1, -1);
  } else {
    console.log('came from nowhere');
    /* TODO: back = '/en-US/404/' */
  }

  $scope.screen_wizard="";
  
  /* get languages */
  $scope.languages = [];
  $http.get(RestURL.baseURL + 'language/get_all_languages/')
    .success(function (data) {
      for (var i = 0; data[i]; ++i) {
        $scope.languages.push({
          id: data[i]['id'],
          iso_id: data[i]['iso_id'],
          iso_code: data[i]['part_1'],
          name: data[i]['ref_name']
        });
      }
      console.log('languages: ');
      console.log($scope.languages);
    })
    .error(function (data) {
      console.log('ERR: language data: ' + data);
    });

  /* Boopathi */
  /* get verbs */
  $http.get(RestURL.baseURL + 'verb/get_all_base_verbs/')
    .success(function (data) {
      $scope.verbs = data;
      $http.get(RestURL.baseURL + 'verb/get_all_verbs_by_base_verb_id/' + ActivityInfo.activity.id)
      .success(function (verbs) {
    	  //console.log('get_all_verbs_by_base_verb_id ' + JSON.stringify(verbs));
    	  $scope.verbs = $scope.verbs.concat(verbs);
    	  //console.log('verbs ' + JSON.stringify($scope.verbs));
      })
      .error(function (err) {
        console.error(err);
      });
    })
    .error(function (err) {
      console.error(err);
    });

  /* watch widgetCounter if it reach 50 get next set */
  $scope.$watch('widgetCount', function () {
    if ($scope.widgetCount === 49) {
      $http.get(RestURL.baseURL + 'keygen/get_max_widget_count/')
        .success(function (data) {
          $scope.widgetIndex = data;
          $scope.widgetCount = -1;
        })
        .error(function (err) {
          console.error(err);
        });
    }
  });

  /* === Tabs ==== */
  /* Boopathi */

  /* the widget counter */
  $http.get(RestURL.baseURL + 'keygen/get_max_widget_count/')
    .success(function (data) {
      $scope.widgetIndex = data;
      $scope.widgetCount = -1;
      switch ($routeParams['action']) {
        case 'create':
          /* set screen name */
          $scope.screen_name = 'Screen_' + $scope.widgetIndex[++$scope.widgetCount];
      }
    })
    .error(function (err) {
      console.error(err);
    });

  /* get screens from activity */
  $http.get(RestURL.baseURL + 'android/get_all_screens_by_activity_id/?activity_id=' + ActivityInfo.activity.id)
    .success(function (data) {
      $scope.screens = data;
    })
    .error(function () {
      console.log('Unable to fetch screen data');
    });

  /* make components draggable */
  $('.menu ul li img').each(function (index, node) {
    new DragObject(node);
  });

  /* make screen drop target */
  new DropTarget($('#screen')[0]);

  /* controls menu */
  $('.controls').find('.panel .panel-heading a').on('click', function (e) {
    e.preventDefault();

    var panel = $(this).attr('href');
    var parent = $(panel).parent('.panel');

    parent.toggleClass('active').siblings().removeClass('active');
  });

  /* delete component */
  $('.delete button').on('click', function () {
    DeleteComponent($scope);
  });

  /*
   delete component functionality should activate
   when shift + backspace are pressed

   16 - shift
   8 - backspace
   */
  var map = {16: false, 8: false};
  $(document).on('keydown', function (event) {
    if (event.which in map) {
      /* shift + backspace in the right order */
      if (event.which === 16 || map[16]) {
        map[event.which] = true;
      }
      if (map[16] && map[8]) {
        DeleteComponent($scope);
      }
    }
  }).on('keyup', function (event) {
    if (event.which in map) {
      map[event.which] = false;
    }
  });

  /* go back on cancel button click */
 /* $('.cancel').on('click', function () {
    console.log($routeParams['action'] + ' cancelled.');
    $scope.$apply(function () {
    	$location.url($scope.back);
    });
  });*/

  /* set screen language */
  $scope.setScreenLanguage = function () {
    if ($scope.screen_name.charAt($scope.screen_name.length - 3) == '-') {
      $scope.screen_name = $scope.screen_name.substr(0, $scope.screen_name.length - 3);
      $scope.screen_name = $scope.screen_name + '-' + $scope.screen_language['iso_code'];
    }
  };

  $scope.cameraproperties={"element_camera_quality":0,"element_camera_destinationType":""};
  $scope.camera_attributes_array=[];
  $scope.rangeproperties={"element_min":0,"element_max":0,"element_value":0}
  $scope.range_attributes_array=[];
  
  $scope.map={"location":"Chennai, IN"};
  
  /* set screen info */
  $scope.setScreenInfo = function () {
    var language = ($scope.screen_language) ? $scope.screen_language : $scope.languages[1];
    //if($scope.wizards.length>0)
    //var selectedwizard = ($scope.screen_wizard) ? $scope.screen_wizard : $scope.wizards[1];
    
    /* set screen name */
    if ($scope.screen_name.charAt($scope.screen_name.length - 3) != '-') {
      $scope.screen_name = $scope.screen_name + '-' + language['iso_code'];
    }

    nodeTree['screen']['name'] = $scope.screen_name;
    /* set screen label */
    nodeTree['screen']['label'] = $scope.screen_label;
    /* set screen language */
    nodeTree['screen']['human_language_id'] = language['id'];
    /* set screen description */
    nodeTree['screen']['description'] = $scope.screen_description;
    
    
    /*SureshAnand*/
        /*set screen role*/
        nodeTree['screen']['role'] = $scope.screen_role;
    
    /* set screen notes (html) */
    /* Boopathi */
    nodeTree['screen']['notes'] = $scope.screen_notes;
    console.log("shuld be here"+$scope.element_name);
    nodeTree['screen']['wizard_id'] = $scope.screen_wizard;
    
    if($scope.element_name){
	    var index = $scope.element_name.indexOf("Range_");
	    if(index == -1){
		    $scope.camera_attributes_array.push($scope.cameraproperties.element_camera_quality);
		    $scope.camera_attributes_array.push($scope.cameraproperties.element_camera_destinationType.value)
		    $scope.element['extended_attributes'] = $scope.camera_attributes_array.join(',');
	
	    }else if(index > -1){
	    	console.log("2222222222222222222222222222"+$scope.rangeproperties.element_min);
		    $scope.range_attributes_array.push($scope.rangeproperties.element_min);
		    $scope.range_attributes_array.push($scope.rangeproperties.element_max);
		    $scope.range_attributes_array.push($scope.rangeproperties.element_value);
		    
		    $scope.element['extended_attributes'] = $scope.range_attributes_array.join(',');
	    }
	  }
  }; 

  /* set element name */
  $scope.setElementName = function () {
    find($scope.element_id, nodeTree['screen']['children'])['name'] = $scope.element_name;
  };

  /* set element description */
  $scope.setElementDescription = function () {
    find($scope.element_id, nodeTree['screen']['children'])['description'] = $scope.element_description;
  };

  /* set element notes */
  $scope.setElementNotes = function () {
    find($scope.element_id, nodeTree['screen']['children'])['notes'] = $scope.element_notes;
  };

  /* set image */
  $scope.setImage = function (element) {
    $timeout(function () {
      $('#' + $scope.element_id).find('.image-default').attr('src', $scope.image.dataURL);
      var filesize = element.files[0].size/1024/1024;
      if(filesize >= 2){
    	  openDialog($modal, 'The selected file should be less than 2MB in size !')
          .result.then(function (dataFromModal) {
          $scope.image = undefined;
          }, function () {
          //$('#verb_info').removeClass('hidden');
        });
        return false;
      }else{
    	  find($scope.element_id, nodeTree['screen']['children'])['image_src'] = $scope.image.dataURL;
      }
    }, 100);
  };

  //set default values
 // $scope.camera_attributes_array.push($scope.element_camera_quality);
  
  /*set camera quality*/
  $scope.setElementCameraQuality = function(){
	  find($scope.element_id, nodeTree['screen']['children'])['camera_quality'] = $scope.element_camera_quality;
  }
  
  /*set camera destination type*/  
  $scope.setElementCameraDestinationType = function(){
  }

  
  $scope.setMapLocation = function() {
	  console.log("-------MAP ELEMENT---->>>>>>>..", $scope.element_id);
  	  find($scope.element_id, nodeTree['screen']['children'])['map-input'] = $scope.map.location;
      var $element_id = $('#' + $scope.element_id);
      $('#' + $scope.element_id).find('.map-image-default').attr('src', $scope.image.dataURL);

  }
  
  /* Boopathi */
  /* === Tab ==== */

  /* set tab label */
  $scope.setTabLabel = function () {
    if ($scope.element_tab) {
      var panel = find($scope.element_id, nodeTree['screen']['children'])['children'];
      /* iterate panel and compare tab - to get selected tab */
      $.each(panel, function (index, value) {
        if (value === $scope.element_tab) {
          /* apply label */
          $('a[data-ng-href=#' + index + ']').text($scope.element_tab.label);
          /* update nodeTree */
          find($scope.element_id, nodeTree['screen']['children'])['children'][index]['label'] = $scope.element_tab['label'];
          return false;
        }
      });
    }
  };

  /* set tab name */
  $scope.setTabName = function () {
    if ($scope.element_tab) {
      var panel = find($scope.element_id, nodeTree['screen']['children'])['children'];
      /* iterate panel and compare tab - to get selected tab */
      $.each(panel, function (index, value) {
        if (value === $scope.element_tab) {
          /* update nodetree */
          find($scope.element_id, nodeTree['screen']['children'])['children'][index]['name'] = $scope.element_tab['name'];
          return false;
        }
      });
    }
  };

  /* add tab */
  $scope.addTab = function () {

    /* find max id and increment it for next tab */
    window.id = nodeTree['screen']['children'] ? maxID(nodeTree['screen']['children']) : 0;
    window.id++;

    /* add tab to the nodeTree */
    find($scope.element_id, nodeTree['screen']['children'])['children'][id] = {
      id: 0,
      type: '',
      name: 'Tab_' + $scope.widgetIndex[++$scope.widgetCount],
      description: '',
      label: 'Tab ' + (Object.keys($scope.element_tabs).length + 1),
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
      height: 0,
      section_position: (Object.keys($scope.element_tabs).length + 1)
    };

    /* show added tabs in the select */
    $scope.element_tabs = find($scope.element_id, nodeTree['screen']['children'])['children'];

    /* select added tab */
    $scope.element_tab = $scope.element_tabs[Object.keys($scope.element_tabs)[0]];

    /* add tab to the tab view */
    $('#' + $scope.element_id + ' ul').append('<li><a data-ng-href=#' + id + '> Tab ' + (Object.keys($scope.element_tabs).length) + '</li>');

    /* add tab to the tab view */
    $('#' + $scope.element_id + ' div[data-role="tab-content"]').append('<div data-role="section" class="section" id=' + id + '></div>');

    /* TODO: incremented 2 to avoid id conflicts  */
    /* increment it for next drag element */
    id = id + 2;
  };

  /* remove tab */
  $scope.removeTab = function () {
    if (Object.keys($scope.element_tabs).length > 2) {
      var panel = find($scope.element_id, nodeTree['screen']['children'])['children'];
      /* iterate panel to get selected tab */
      $.each(panel, function (key, value) {
        if (value === $scope.element_tab) {
          /* remove tab header */
          $('#' + $scope.element_id + ' ul').find('li a[data-ng-href=#' + key + ']').parent().remove();

          /* remove tab panel */
          $('#' + $scope.element_id + ' div[data-role="tab-content"]').find('div[id=' + key + ']').remove();

          /* delete tab object in the nodeTree */
          delete find($scope.element_id, nodeTree['screen']['children'])['children'][key];

          return false;
        }
      });
      /* select all tabs */
      $scope.element_tabs = find($scope.element_id, nodeTree['screen']['children'])['children'];

      /* select first tab */
      $scope.element_tab = $scope.element_tabs[Object.keys($scope.element_tabs)[0]];
    }
  };

  /* move tab up */
  $scope.moveTabUp = function () {
    if ($scope.element_tab) {
      var tabs = find($scope.element_id, nodeTree['screen']['children'])['children'];
      var selectedTab = 0;
      /* get selected tab */
      $.each(tabs, function (key, value) {
        if (value === $scope.element_tab) {
          selectedTab = key;
        }
      });

      var index = Object.keys(tabs).indexOf(selectedTab);

      if (index > 0) {
        /* swap elements in the nodeTree */
        var temp = tabs[selectedTab];
        tabs[selectedTab] = tabs[Object.keys(tabs)[index - 1]];
        tabs[Object.keys(tabs)[index - 1]] = temp;

        $.each(tabs, function (key, value) {
      	  var newValue = parseInt(Object.keys(tabs).indexOf(key)) + 1;
      	  find($scope.element_id, nodeTree['screen']['children'])['children'][key]['section_position'] = newValue;
        });
        
        /* swap tab in the tabs */
        swapTabs(tabs);
      }
    }
  };

  /* move tab down */
  $scope.moveTabDown = function () {
    if ($scope.element_tab) {
      var tabs = find($scope.element_id, nodeTree['screen']['children'])['children'];
      var selectedTab = 0;
      /* get selected tab */
      $.each(tabs, function (key, value) {
        if (value === $scope.element_tab) {
          selectedTab = key;
        }
      });

      var index = Object.keys(tabs).indexOf(selectedTab);

      if (index < Object.keys(tabs).length - 1) {
        /* swap elements in the nodeTree */
        var temp = tabs[selectedTab];
        tabs[Object.keys(tabs)[index]] = tabs[Object.keys(tabs)[index + 1]];
        tabs[Object.keys(tabs)[index + 1]] = temp;

        $.each(tabs, function (key, value) {
        	  var newValue = parseInt(Object.keys(tabs).indexOf(key)) + 1;
        	  find($scope.element_id, nodeTree['screen']['children'])['children'][key]['section_position'] = newValue;
        });
        
        /* swap tab in the tabs */
        swapTabs(tabs);
      }
    }
  };

  var swapTabs = function (tabs) {
    /* swap tab in the tabs */
    $('#' + $scope.element_id + ' ul').find('li').remove();
    $('#' + $scope.element_id + ' div[data-role="tab-content"]').find('div').remove();

    /* object key and value is not swapping, so re-creating tabs */
    $.each(tabs, function (key, value) {
      $('#' + $scope.element_id + ' ul').append('<li><a data-ng-href=#' + key + '>' + value.label + '</li>');
      $('#' + $scope.element_id + ' div[data-role="tab-content"]').append('<div data-role="section" class="section" id=' + key + '></div>');
    });
  };

  /* === Data Binding ==== */

  /* get noun data */
  $scope.getNounData = function () {
    var contain = false;

    switch ($scope.element['data_binding_context']) {
      case 'not_bound':
        /* set noun type */
        $scope.noun_type = 'none';

        $scope.pn_attribute_id = undefined;
        $scope.pn_attribute = undefined;

        $scope.secondary_noun = undefined;
        $scope.sn_attribute = undefined;

        /* hide grid data */
        $('.grid-data').removeClass('active');

        /* hide primary and secondary nouns form */
        $('.primary_noun_form, .secondary_nouns_form').removeClass('active');

        break;
      case 'primary_noun':
    	  console.log("inside prim noun");

    	/* set noun type */
        $scope.noun_type = 'primary';

        /* hide secondary nouns form */
        $('.secondary_nouns_form').removeClass('active');

        /* show primary noun form */
        $('.primary_noun_form').addClass('active');

        /* grid scenario */
        if ($scope.element_type === 'table') {
          /* show grid data */
          $('.grid-data').addClass('active');
          $('.grid-data').removeClass('hidden');
          $('.grid-data').addClass('visible');
        }else{
          $('.grid-data').addClass('hidden');
	      $('.grid-data').removeClass('active');            
	      $('.grid-data').removeClass('visible');
	      $scope.element_column = undefined;
	      //$scope.pn_attribute_id = undefined;
        }
        
        /* set primary_noun */
        $scope.primary_noun = ActivityInfo.activity.primary_noun;

        /* show selected primary noun attributes */
        for (var i = 0; i < $scope.primary_noun['nounattributes'].length; i++) {
          if ($scope.element_column) {
            if ($scope.element_column['noun_attribute_id'] == $scope.primary_noun['nounattributes'][i]['id']) {
              $scope.pn_attribute_id = '' + $scope.primary_noun['nounattributes'][i]['id'];
              $scope.pn_attribute = $scope.primary_noun['nounattributes'][i];
              contain = true;
            }
          }
          else {
            if ($scope.element['noun_attribute_id'] == $scope.primary_noun['nounattributes'][i]['id']) {
              $scope.pn_attribute_id = '' + $scope.primary_noun['nounattributes'][i]['id'];
              $scope.pn_attribute = $scope.primary_noun['nounattributes'][i];
              contain = true;
            }
          }
        }

        $scope.pn_attribute_id = (contain) ? $scope.pn_attribute_id : undefined;
        $scope.pn_attribute = (contain) ? $scope.pn_attribute : undefined;

        /* hide create primary noun button */
        $('.create_primary_noun').addClass('hidden');

        /* show primary noun data */
        $('.primary_noun').addClass('active');


        break;
      case 'secondary_noun':
        /* set noun type */
        $scope.noun_type = 'secondary';

        /* hide primary noun form */
        $('.primary_noun_form').removeClass('active');

        /* show secondary nouns form */
        $('.secondary_nouns_form').addClass('active');

        /* grid scenario */
        if ($scope.element_type === 'table') {
          /* show grid data */
          $('.grid-data').addClass('active');
          $('.grid-data').removeClass('hidden');
          $('.grid-data').addClass('visible');
        }else{
          $('.grid-data').addClass('hidden');
	      $('.grid-data').removeClass('active');            
	      $('.grid-data').removeClass('visible');
	      $scope.element_column = undefined;
        }

        /* show selected secondary noun */
        $scope.secondary_nouns = ActivityInfo.activity.secondary_nouns;
        for (var j = 0; j < $scope.secondary_nouns.length; ++j) {
          if ($scope.element.columns && $scope.element_type === 'table') {
            for (var c = 0; c < $scope.element.columns.length; ++c) {
              if ($scope.element.columns[c]['noun_id'] === $scope.secondary_nouns[j]['id']) {
                $scope.secondary_noun = $scope.secondary_nouns[j];
              }
            }
          } else {
            if ($scope.element['noun_id'] === $scope.secondary_nouns[j]['id']) {
              $scope.secondary_noun = $scope.secondary_nouns[j];
            }
          }
        }

        /* show selected secondary noun attribute */
        for (var k = 0; k < $scope.secondary_noun['nounattributes'].length; ++k) {
          if ($scope.element_column) {
            if ($scope.element_column['noun_attribute_id'] === $scope.secondary_noun['nounattributes'][k]['id']) {
              $scope.sn_attribute = $scope.secondary_noun['nounattributes'][k];
              if($scope.sn_attribute.label === 'Label'){
            	  $scope.sn_attribute.label =  $scope.secondary_noun['nounattributes'][k].name; 
              }
              $scope.sn_attribute_id = $scope.secondary_noun['nounattributes'][k]['id'];
              contain = true;
            }
          } else {
            if ($scope.element['noun_attribute_id'] === $scope.secondary_noun['nounattributes'][k]['id']) {
              $scope.sn_attribute = $scope.secondary_noun['nounattributes'][k];
              if($scope.sn_attribute.label === 'Label' || $scope.sn_attribute.label === ''){
            	  $scope.sn_attribute.label =  $scope.secondary_noun['nounattributes'][k].name; 
              }
              $scope.sn_attribute_id = $scope.secondary_noun['nounattributes'][k]['id'];
              contain = true;
            }
          }
        }

        $scope.sn_attribute = (contain) ? $scope.sn_attribute : undefined;
        $scope.sn_attribute_id = (contain) ? $scope.sn_attribute_id : undefined;

        /* hide no secondary nouns message */
        $('.no_secondary_nouns_message').addClass('hidden');

        /* show secondary nouns data */
        $('.secondary_nouns').addClass('active');

        break;
    }
  };

  /* choose noun */
  $scope.chooseNoun = function () {
    var i, j, k;
    var grid = ($scope.element_type === 'table') ? $scope.element : undefined;
    var JSON_grid = (grid) ? _find(grid.name, 'name', nodeTree['screen']['children']) : undefined;
    var element = (grid) ? $scope.element_column : $scope.element;
    var JSON_element = _find(element.name, 'name', nodeTree['screen']['children']);

    /* choose noun type */
    switch ($scope.noun_type) {
      case 'none':
        /* reset primary noun */
        $scope.pn_attribute_id = undefined;
        $scope.pn_attribute = undefined;

        /* reset secondary nouns */
        $scope.secondary_noun = undefined;
        $scope.sn_attribute = undefined;
        //$scope.sn_attribute_id = undefined;

        /* clear data from scope element */
        element['data_binding_context'] = 'not_bound';
        element['noun_id'] = 0;
        element['noun_attribute_id'] = 0;
        element['extended_attributes'] = '';

        /* save to JSON */
        JSON_element['data_binding_context'] = 'not_bound';
        JSON_element['noun_id'] = 0;
        JSON_element['noun_attribute_id'] = 0;
        JSON_element['extended_attributes'] = '';

        /* hide grid data */
        $('.grid-data').removeClass('active');

        /* hide primary and secondary noun data */
        $('.primary_noun_form, .secondary_nouns_form').removeClass('active');

        /* grid scenario */
        if (grid) {
          /* set not_bound to scope columns */
          for (i = 0; i < grid.columns.length; ++i) {
            grid['columns'][i]['data_binding_context'] = 'not_bound';
            grid['columns'][i]['noun_id'] = 0;
            grid['columns'][i]['noun_attribute_id'] = 0;
            grid['columns'][i]['extended_attributes'] = '';
          }

          /* save to JSON */
          for (i = 0; i < JSON_grid.columns.length; ++i) {
            JSON_grid['columns'][i]['data_binding_context'] = 'not_bound';
            JSON_grid['columns'][i]['noun_id'] = 0;
            JSON_grid['columns'][i]['noun_attribute_id'] = 0;
            JSON_grid['columns'][i]['extended_attributes'] = '';
          }

          /* set not_bound to scope grid */
          grid['data_binding_context'] = 'not_bound';

          /* save to JSON */
          JSON_grid['data_binding_context'] = 'not_bound';
        }

        break;
      case 'primary':
        /* grid scenario */
        if (grid) {
          /* set not_bound to secondary noun columns */
          for (j = 0; j < grid.columns.length; ++j) {
            if (grid['columns'][j]['data_binding_context'] === 'secondary_noun') {
              grid['columns'][j]['data_binding_context'] = 'not_bound';
              grid['columns'][j]['noun_id'] = 0;
              grid['columns'][j]['noun_attribute_id'] = 0;
              grid['columns'][j]['extended_attributes'] = '';
            }
          }

          /* save to JSON */
          for (j = 0; j < JSON_grid.columns.length; ++j) {
            if (JSON_grid['columns'][j]['data_binding_context'] === 'secondary_noun') {
              JSON_grid['columns'][j]['data_binding_context'] = 'not_bound';
              JSON_grid['columns'][j]['noun_id'] = 0;
              JSON_grid['columns'][j]['noun_attribute_id'] = 0;
              JSON_grid['columns'][j]['extended_attributes'] = '';
            }
          }

          /* show grid data */
          $('.grid-data').addClass('active');
        }

        $scope.primary_noun = ActivityInfo.activity.primary_noun;
        $scope.pn_attribute_id = undefined;
        $scope.pn_attribute = undefined;

        /* show primary noun form */
        $('.primary_noun_form').addClass('active');

        /* in case there is no primary noun */
        if ($.isEmptyObject($scope.primary_noun)) {
          /* hide primary noun data */
          $('.primary_noun').removeClass('active');
          /* show create primary noun button */
          $('.create_primary_noun').removeClass('hidden');
        } else {
          /* show primary noun data */
          $('.primary_noun').addClass('active');
          /* hide create primary noun button */
          $('.create_primary_noun').addClass('hidden');
        }

        /* hide secondary nouns data */
        $('.secondary_nouns_form').removeClass('active');

        break;
      case 'secondary':
        /* grid scenario */
        if (grid) {
          /* set not_bound to secondary noun columns */
          for (k = 0; k < grid.columns.length; ++k) {
            if (grid['columns'][k]['data_binding_context'] === 'primary_noun') {
              grid['columns'][k]['data_binding_context'] = 'not_bound';
              grid['columns'][k]['noun_id'] = 0;
              grid['columns'][k]['noun_attribute_id'] = 0;
              grid['columns'][k]['extended_attributes'] = '';
            }
          }

          /* save to JSON */
          for (k = 0; k < JSON_grid.columns.length; ++k) {
            if (JSON_grid['columns'][k]['data_binding_context'] === 'primary_noun') {
              JSON_grid['columns'][k]['data_binding_context'] = 'not_bound';
              JSON_grid['columns'][k]['noun_id'] = 0;
              JSON_grid['columns'][k]['noun_attribute_id'] = 0;
              JSON_grid['columns'][k]['extended_attributes'] = '';
            }
          }

          /* show grid data */
          $('.grid-data').addClass('active');
        }

       $scope.secondary_nouns = ActivityInfo.activity.secondary_nouns;
       if($scope.secondary_nouns || $scope.secondary_nouns.length > 0){
        	for(var i=0;i<$scope.secondary_nouns.length;i++){
		    	if($scope.secondary_nouns[i].label === "" || $scope.secondary_nouns[i] === null){
		    		$scope.secondary_nouns[i].label = $scope.secondary_nouns[i].name;
		    	}
		    	if($scope.secondary_nouns[i].nounattributes || $scope.secondary_nouns[i].nounattributes.length > 0){
		        	for(var j=0;j<$scope.secondary_nouns[i].nounattributes.length;j++){
				    	if($scope.secondary_nouns[i].nounattributes[j].label === "Label" || $scope.secondary_nouns[i].nounattributes[j].label === "" || $scope.secondary_nouns[i].nounattributes[j] === null){
				    		$scope.secondary_nouns[i].nounattributes[j].label = $scope.secondary_nouns[i].nounattributes[j].name;
				    	}
		        	}
		        }
        	}
        }
       
       
       
        $scope.secondary_noun = undefined;
        $scope.sn_attribute = undefined;
        $scope.sn_attribute_id = undefined;
        
        
        /* hide primary noun form */
        $('.primary_noun_form').removeClass('active');

        /* in case there are no secondary nouns */
        if (!$scope.secondary_nouns || $scope.secondary_nouns.length === 0) {
          /* hide secondary nouns data */
          $('.secondary_nouns').removeClass('active');
          /* show no secondary nouns message */
          $('.no_secondary_nouns_message').removeClass('hidden');
        } else {
          /* show secondary nouns data */
          $('.secondary_nouns').addClass('active');
          /* hide no secondary nouns message */
          $('.no_secondary_nouns_message').addClass('hidden');
        }

        /* show secondary nouns from */
        $('.secondary_nouns_form').addClass('active');
        break;
    }
  };

  /* create primary noun modal */
  $scope.createPrimaryNoun = function () {
    var modalInstance = $modal.open({
      backdrop: true,
      backdropClick: true,
      dialogFade: false,
      keyboard: true,
      templateUrl: 'app/views/en-US/templates/modals/axe/create_primary_noun.html',
      controller: 'NounModalCtrl',
      resolve: {}
    });
  };

  $scope.chooseColumn = function () {
    switch ($scope.noun_type) {
      case 'none':
        break;
      case 'primary':
        $scope.pn_attribute_id = '';
        $scope.pn_attribute = '';
        for (var i = 0; i < $scope.primary_noun.nounattributes.length; ++i) {
          if ($scope.element_column['noun_attribute_id'] === $scope.primary_noun.nounattributes[i]['id']) {
            $scope.pn_attribute_id = '' + $scope.primary_noun.nounattributes[i]['id'];
            $scope.pn_attribute = $scope.primary_noun.nounattributes[i];
          }
        }
        break;
      case 'secondary':
        $scope.sn_attribute = '';
        for (var j = 0; j < $scope.secondary_noun.nounattributes.length; ++j) {
          if ($scope.element_column['noun_attribute_id'] === $scope.secondary_noun.nounattributes[j]['id']) {
            $scope.sn_attribute = $scope.secondary_noun.nounattributes[j];
          }
        }
        break;
    }
  };

  /* TODO: test $scope.pn_attribute usage */
  /* choose primary noun attribute */
  $scope.choosePnAttr = function () {
    /* select primary noun attribute */
    for (var i = 0; i < $scope.primary_noun.nounattributes.length; i++) {
      if ($scope.primary_noun.nounattributes[i]['id'] == $scope.pn_attribute_id) {
        $scope.pn_attribute = $scope.primary_noun.nounattributes[i];
      }
    }

    var grid = ($scope.element_type === 'table') ? $scope.element : undefined;
    var JSON_grid = (grid) ? _find(grid.name, 'name', nodeTree['screen']['children']) : undefined;
    var element = (grid) ? $scope.element_column : $scope.element;
    var JSON_element = _find(element.name, 'name', nodeTree['screen']['children']);

    if ($scope.pn_attribute) {
      /* set scope element data */
      element['data_binding_context'] = 'primary_noun';
      element['noun_id'] = $scope.primary_noun.id;
      element['noun_attribute_id'] = $scope.pn_attribute.id;
      element['extended_attributes'] = '';

      /* save data to JSON */
      JSON_element['data_binding_context'] = 'primary_noun';
      JSON_element['noun_id'] = $scope.primary_noun.id;
      JSON_element['noun_attribute_id'] = $scope.pn_attribute.id;
      JSON_element['extended_attributes'] = '';

      /* grid scenario */
      if (grid) {
        /* set data_binding_context to scope grid */
        grid['data_binding_context'] = 'primary_noun';

        /* save to JSON */
        JSON_grid['data_binding_context'] = 'primary_noun';
      }
    } else {
      /* reset scope element data */
      element['data_binding_context'] = 'not_bound';
      element['noun_id'] = undefined;
      element['noun_attribute_id'] = undefined;
      element['extended_attributes'] = undefined;

      /* save data to JSON */
      JSON_element['data_binding_context'] = 'not_bound';
      JSON_element['noun_id'] = undefined;
      JSON_element['noun_attribute_id'] = undefined;
      JSON_element['extended_attributes'] = undefined;
    }

    console.log('Primary Noun: ');
    console.log(nodeTree['screen']);
  };

  /* choose secondary noun attribute */
  $scope.chooseSnAttr = function () {
    var grid = ($scope.element_type === 'table') ? $scope.element : undefined;
    var JSON_grid = (grid) ? _find(grid.name, 'name', nodeTree['screen']['children']) : undefined;
    var element = (grid) ? $scope.element_column : $scope.element;
    var JSON_element = _find(element.name, 'name', nodeTree['screen']['children']);

    /*for (var i = 0; i < $scope.secondary_noun.nounattributes.length; i++) {
        if ($scope.secondary_noun.nounattributes[i]['id'] == $scope.sn_attribute_id) {
          $scope.sn_attribute = $scope.secondary_noun.nounattributes[i];
        }
      }*/
    if ($scope.sn_attribute) {
      /* set scope element data */
      element['data_binding_context'] = 'secondary_noun';
      element['noun_id'] = $scope.secondary_noun.id;
      element['noun_attribute_id'] = $scope.sn_attribute.id;
      element['extended_attributes'] = '';

      /* save data to JSON */
      JSON_element['data_binding_context'] = 'secondary_noun';
      JSON_element['noun_id'] = $scope.secondary_noun.id;
      JSON_element['noun_attribute_id'] = $scope.sn_attribute.id;
      JSON_element['extended_attributes'] = '';

      /* grid scenario */
      if (grid) {
        /* set data_binding_context to scope grid */
        grid['data_binding_context'] = 'secondary_noun';

        /* save to JSON */
        JSON_grid['data_binding_context'] = 'secondary_noun';
      }
    } else {
      /* reset scope element data */
      element['data_binding_context'] = 'not_bound';
      element['noun_id'] = undefined;
      element['noun_attribute_id'] = undefined;
      element['extended_attributes'] = undefined;

      /* save data to JSON */
      JSON_element['data_binding_context'] = 'not_bound';
      JSON_element['noun_id'] = undefined;
      JSON_element['noun_attribute_id'] = undefined;
      JSON_element['extended_attributes'] = undefined;
    }

    console.log('Secondary Noun: ');
    console.log(nodeTree['screen']);
  };

  /* === Verb Binding ==== */

  /* add custom event */
  $scope.addEvent = function () {
    alert('Not implemented!');
  };

  /* choose event */
  $scope.chooseEvent = function () {
    if ($scope.verb) {
      find($scope.element_id, nodeTree['screen']['children'])['event_verb_combo'] = $scope.event + ',' + $scope.verb.action_on_data + ';';
      find($scope.element_id, nodeTree['screen']['children'])['verb_binding_context'] = 'bound';
      /* show BPMN diagram */
      switch ($scope.verb) {
        case 'CREATE':
          $('.bpmn-diagram').addClass('active');

          break;
        default:
          $('.bpmn-diagram').removeClass('active');
      }
    }
  };

  /* choose verb */
  $scope.chooseVerb = function () {
	  //console.log('verb selected ' + $scope.verb);
    if ($scope.event) {
      switch ($scope.element_type) {
        case 'screen':
          if ($scope.verb) {
            nodeTree['screen']['event_verb_combo'] = $scope.event + ',' + $scope.verb.action_on_data + ';';
            nodeTree['screen']['verb_binding_context'] = 'bound';
          } else {
            nodeTree['screen']['event_verb_combo'] = '';
            nodeTree['screen']['verb_binding_context'] = 'not_bound';
          }

          break;
        default:
          if ($scope.verb) {
        	  console.log($scope.event + ',' + $scope.verb + ';');
            find($scope.element_id, nodeTree['screen']['children'])['event_verb_combo'] = $scope.event + ',' + $scope.verb.action_on_data + ';';
            find($scope.element_id, nodeTree['screen']['children'])['verb_binding_context'] = 'bound';
            //
            console.log(JSON.stringify($scope.verb));
            if($scope.verb.action_on_data == 'GpComponentVerb'){
            	var extra_verb_info = {"verb_id": $scope.verb.id}
            	//map values
            	var map_parameters = [];
            	var nount_attrs = ActivityInfo.activity.primary_noun.nounattributes;
            	nount_attrs.forEach(function(noun_attr) {
            	    var verb_input_parameters = JSON.parse($scope.verb.actual_information).input_parameters;
            	    verb_input_parameters.forEach(function(verb_input_parameter) {
            	    	if(noun_attr.name.toLowerCase() == verb_input_parameter.name.toLowerCase()){
            	    		var map_parameter = {
            	    			"noun_attr_id": noun_attr.id,
            	    			"verb_parameter_name":verb_input_parameter.name,
            	    			"parameter_order": verb_input_parameter.order
            	    		};
            	    		map_parameters.push(map_parameter);
            	    	}
            	    });
            	});
            	extra_verb_info.map_parameters = map_parameters;
            	//console.log(JSON.stringify(extra_verb_info))
            	find($scope.element_id, nodeTree['screen']['children'])['extra_verb_info'] = JSON.stringify(extra_verb_info);
            }
          } else {
            find($scope.element_id, nodeTree['screen']['children'])['event_verb_combo'] = '';
            find($scope.element_id, nodeTree['screen']['children'])['verb_binding_context'] = 'not_bound';
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

      /* show verb target screen */
      if($scope.verb)
      switch ($scope.verb.action_on_data) {
        case 'GpSearchDetail':
          openDialog($modal, 'The selected verb requires a target screen, please select a screen from the list !')
            .result.then(function (dataFromModal) {
          }, function () {
        	  $('#customVerb_info').addClass('hidden');
            $('#verb_info').removeClass('hidden');
          });
          
        case 'GpCustom':
        	var customVerbData = {};
        	var self = $scope;
        	self.customVerb = [];
        	self.verbSection = {
      			  value: "activity_verb"
      	  };
        	$http.get(RestURL.baseURL + 'activity/search_for_update/?activity_id='+ ActivityInfo.activityId)
        	.success(function(data){
        		$('#verb_info').addClass('hidden');
        		$('#customVerb_info').removeClass('hidden');
        		customVerbData=data;
        		if(angular.equals(customVerbData.module_type,"default_wsdl")){
          		  //need to call wsdl table for get all operation
          		  $http.get(RestURL.baseURL+'wsdlNoun/get_operations_by_wsdl_id/?wsdl_id='+customVerbData.wsdl_id)
          		  .success(function (response){
          			  self.wsdlVerbs = response;
          			  self.verbSection.value ="wsdl_custom_verb";
                        angular.copy(self.wsdlVerbs, self.customVerb);
          		  }).error(function (response){
          			  $log.debug("****************** ERROR");
          		  });
          	  }else{
          		  $http.get(RestURL.baseURL + 'verb/get_all_verbs_by_activity/?activity_id=' + ActivityInfo.activityId)
                    .success(function (response) {
                  	  self.verbSection.value ="activity_verb";
                      self.allVerbs = response;
                      angular.copy(self.allVerbs, self.verbs);
                    }).error(function (response) {
                    $log.debug("unable to get the activity to update!");
                  });
          	  }
        	}).error(function(errordata){
        		console.error("error occured!");
        	});

        	
          break;
        default:
          $('#verb_info').addClass('hidden');
          $('#customVerb_info').addClass('hidden');
      }
    }
  };

  /* select the target screen for verb */
  $scope.chooseTargetScreen = function () {
    find($scope.element_id, nodeTree['screen']['children'])['extra_verb_info'] = $scope.extra_verb_info;
  };

 
  /* === Copy Screen ==== */

  $scope.CopyScreen = function () {
    var url;

    switch ($scope.name) {
      case 'desktop':
        url = RestURL.baseURL + 'pc/search_for_update/?screen_id=' + $scope.screen;

        break;
      case 'tablet':
      case 'mobile':
        url = RestURL.baseURL + 'android/search_for_update/?screen_id=' + $scope.screen;

        break;
    }

    /* get selected screen */
    $http.get(url)
      .success(function (data) {
        /* iterate through all the children to make id, parent_id and screen_id = 0 */
        var children = data['children'] ? data['children'] : {};

        /* find max id and increment it for next drag element */
        window.id = data['children'] ? maxID(data['children']) : 0;
        window.id++;

        for (var firstLevel in children) {
          if (children.hasOwnProperty(firstLevel)) {
            children[firstLevel]['id'] = 0;
            children[firstLevel]['parent_id'] = 0;
            children[firstLevel]['screen_id'] = 0;

            if (!$.isEmptyObject(children[firstLevel]['children'])) {
              for (var secondLevel in children[firstLevel]['children']) {
                if (children[firstLevel]['children'].hasOwnProperty(secondLevel)) {
                  children[firstLevel]['children'][secondLevel]['id'] = 0;
                  children[firstLevel]['children'][secondLevel]['parent_id'] = 0;
                  children[firstLevel]['children'][secondLevel]['screen_id'] = 0;

                  if (!$.isEmptyObject(children[firstLevel]['children'][secondLevel]['children'])) {
                    for (var thirdLevel in children[firstLevel]['children'][secondLevel]['children']) {
                      if (children[firstLevel]['children'][secondLevel]['children'].hasOwnProperty(thirdLevel)) {
                        children[firstLevel]['children'][secondLevel]['children'][thirdLevel]['id'] = 0;
                        children[firstLevel]['children'][secondLevel]['children'][thirdLevel]['parent_id'] = 0;
                        children[firstLevel]['children'][secondLevel]['children'][thirdLevel]['screen_id'] = 0;
                      }
                    }
                  }
                }
              }
            }
          }
        }

        /* replace edited children */
        data['children'] = children;

        /* copy selected screen */
        switch ($scope.name) {
          case 'desktop':
            showDesktopScreen($scope, $compile, data);

            break;
          case 'tablet':
            showTabletScreen($scope, $compile, data);

            break;
          case 'mobile':
            showMobileScreen($scope, $compile, data);

            break;
        }
      })
      .error(function () {
        console.log('Unable to load screen data!');
      });
  };

  /* === CK editor ==== */

  /* Boopathi */

  /* Maximize ckEditor */
  $scope.maximizeScreenNotes = function () {
    $timeout(function () {
      $('#cke_screen_notes .cke_button__maximize').click();
    }, 300);
  };

  $scope.maximizeComponentsNotes = function () {
    $timeout(function () {
      $('#cke_element_notes .cke_button__maximize').click();
    }, 300);
  };

  /**
   * Rashmi , adding wizard flow
  */
  
  
/*  $scope.setWizard = function(){
  	  $http.get(RestURL.baseURL + 'activity/get_wizards/' + ActivityInfo.activity.id)
  	    .success(function (data) {
  	      console.log('wizards: '+data[0]);
  	      $scope.screen_wizard = data[0]['id'];
  	     
  	    var msg = "Screen Added successfully!";
        $scope.openSaveDialog('sm', msg);
  	    })
  	    .error(function (data) {
  	      console.log('ERR: wizards data: ' + data);
  	    });
  };*/
  
  $scope.setWizard = function(){
	  //alert("ActivityInfo.deviceTypes--->"+JSON.stringify(ActivityInfo.deviceTypes));
	  //if($location.url()!='/en-US/axe/mobile/create/'){
		  $http({
	          url: RestURL.baseURL + 'activity/addWizard_screen/',
	          data: angular.toJson(ActivityInfo.deviceTypes),
	          method: 'POST',
	          headers: {
	            'Accept': 'application/json'
	          }
	        })
	          .success(function (response) {
	        	  $scope.showAddWizardBtn=false;
	        	  var msg = "Screen Added successfully!";
	              $scope.openSaveDialog('sm', msg);
	          }).error(function (response) {
	        	  console.log('ERR: wizards data: ' + response);
	          });
	  //}
  };
  
  /**
   * loading activity based on the activity id.
   */
  self.getActivityToUpdate = function () {

    $http.get(RestURL.baseURL + 'activity/search_for_update/?activity_id=' + ActivityInfo.activityId)
      .success(function (response) {
        ActivityInfo.activity = response;
      }).error(function (response) {
      $log.debug("unable to get the activity to update!");
    });
  };
  
  //open add project modal
  $scope.createWizardModal = function () {
    var modalInstance = $modal.open({
      backdrop: true,
      backdropClick: true,
      dialogFade: false,
      templateUrl: 'app/views/en-US/templates/modals/activity/createwizard.html',
      controller: 'WizardModalCtrl',
      resolve: {}
    });
    modalInstance.result.then(function (dataFromModal) {
    	$scope.modalData = dataFromModal;
      	$scope.name = $scope.modalData.name;
      	$scope.description = $scope.modalData.description;
      	$scope.activity_id= ActivityInfo.activityId;
      	
      	//form the post data
      	var wizarddata={
      			id: 0,
      			name:$scope.name,
      			description:$scope.description,
      			activity_id:$scope.activity_id,
      			screenIds:""
      	};
      	//alert(angular.toJson(wizarddata));
      	$http({
            url: RestURL.baseURL + 'activity/create_wizard/',
            data: wizarddata,
            method: 'POST',
            headers: {
                'Accept': 'application/json'
            }
        })
        .success(function (response) {
        	var msg = "Wizard created successfully";
            $scope.openSaveDialog('sm', msg);
            self.getActivityToUpdate();
        })
        .error(function (response) {
          $log.info('error' + response);
        });
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
          });
    };
    
    
    
    /**
     * successfull message modal
    */
    $scope.openSaveDialog = function (size, msg) {
        var modalInstance = $modal.open({
          animation: true,
          size: size,
          templateUrl: 'app/views/en-US/templates/modals/project/savemessage.html',
          controller: 'ModalCtrl',
          resolve: {
            data: function () {
              return msg; // deep copy
            }
          }
        });
        modalInstance.result.then(function (dataFromModal) {

        }, function () {
        console.log('Modal dismissed at: ' + new Date());
        });
      };
      
      $scope.openAssociatePrimaryNoun = function (value) {
    	  if(value == 'add'){
    		  ActivityInfo.associateNounData={}
    	  }
          var modalInstance = $modal.open({
            backdrop: true,
            backdropClick: true,
            dialogFade: false,
            templateUrl: 'app/views/en-US/templates/modals/activity/associatePrimaryNoun.html',
            controller: 'associateNounCtrl',
            resolve: {
          	  data: function () {
                    return ActivityInfo; // deep copy
                  }
            }
          });
          modalInstance.result.then(function (dataFromModal) {
        	  ActivityInfo.associateNounData={};
        	  if(dataFromModal.primary_Noun){
        		$scope.showBtn=true;
          	    find($scope.element_id, nodeTree['screen']['children'])['data_binding_target_noun_id'] = dataFromModal.primary_Noun.id;
          	    ActivityInfo.associateNounData.primary_noun = dataFromModal.primary_Noun;
        	  }
        	  if(dataFromModal.pn_attribute){
        		  find($scope.element_id, nodeTree['screen']['children'])['data_binding_target_noun_attr_id'] = dataFromModal.pn_attribute.id;
        		  ActivityInfo.associateNounData.pn_attribute = dataFromModal.pn_attribute;
        	  }
          }, function () {
        	  console.log('Modal dismissed at: ' + new Date());
          });
       };
      
       // create checkbox group
       $scope.createCheckboxGroup = function () {

    	   var modalInstance = $modal.open({
             backdrop: true,
             backdropClick: true,
             dialogFade: false,
             templateUrl: 'app/views/en-US/templates/modals/activity/createGroup.html',
             controller: 'GroupModalCtrl',
             resolve: {
           	  data: function () {
                     return ActivityInfo; // deep copy
                   }
             }
           });
           modalInstance.result.then(function (dataFromModal) {
        	   ActivityInfo.associateNounData={};
         	  if(dataFromModal.primary_Noun){
           	    //find($scope.element_id, nodeTree['screen']['children'])['data_binding_target_noun_id'] = dataFromModal.primary_Noun.id;
           	    ActivityInfo.associateNounData.primary_noun = dataFromModal.primary_Noun.id;
         	  }
         	  if(dataFromModal.pn_attribute){
         		 // find($scope.element_id, nodeTree['screen']['children'])['data_binding_target_noun_attr_id'] = dataFromModal.pn_attribute.id;
         		  ActivityInfo.associateNounData.pn_attribute = dataFromModal.pn_attribute.id;
         	  }
         	  
        	   var groupdata={
             			id: 0,
             			name:dataFromModal.name,
             			screenId: nodeTree['screen']['id'],
             			type:"checkbox",
             			data_binding_context:"primary",
             			noun_attribute_id: ActivityInfo.associateNounData.pn_attribute,
             			noun_id: ActivityInfo.associateNounData.primary_noun
             	};
         	  if(dataFromModal.name){
         		  // save groups in factory 
         		 console.log(",,,,,,,,,,,,,,,,,,,,,"+angular.toJson(ActivityInfo.checkboxgroupArray.checkboxgroup.length));
         		  if(ActivityInfo.checkboxgroupArray.checkboxgroup.length == 0){
         			 groupdata.id = 1;
         		  }else{
         			 for (i = 0; i < ActivityInfo.checkboxgroupArray.checkboxgroup.length; i++) {
         		        if (ActivityInfo.checkboxgroupArray.checkboxgroup[i].name === dataFromModal.name) {
         		        	var msg = "Group already Exist";
             	            $scope.openSaveDialog('sm', msg);
             	            return;
         		        }
         		    }
         			console.log("ActivityInfo.checkboxgroupArray.checkboxgroup.length-1 "+ActivityInfo.checkboxgroupArray.checkboxgroup[ActivityInfo.checkboxgroupArray.checkboxgroup.length-1]);
         			groupdata.id = ActivityInfo.checkboxgroupArray.checkboxgroup[ActivityInfo.checkboxgroupArray.checkboxgroup.length-1].id+1;
         		  }
         		  ActivityInfo.checkboxgroupArray.checkboxgroup.push(groupdata);         		  
         		  
         		 $scope.buttongroups = ActivityInfo.checkboxgroupArray.checkboxgroup;
         		 nodeTree['screen']['checkboxGroupList']= ActivityInfo.checkboxgroupArray.checkboxgroup;
         			
         		  console.log(".........................."+angular.toJson(ActivityInfo.checkboxgroupArray.checkboxgroup));
         	  }
           }, function () {
         	  console.log('Modal dismissed at: ' + new Date());
           });
        };
        
        
        // create radio group
        $scope.createRadioGroup = function () {

     	   var modalInstance = $modal.open({
              backdrop: true,
              backdropClick: true,
              dialogFade: false,
              templateUrl: 'app/views/en-US/templates/modals/activity/createGroup.html',
              controller: 'GroupModalCtrl',
              resolve: {
            	  data: function () {
                      return ActivityInfo; // deep copy
                  }
              }
            });
            modalInstance.result.then(function (dataFromModal) {
            	ActivityInfo.associateNounData={};
           	  if(dataFromModal.primary_Noun){
             	    //find($scope.element_id, nodeTree['screen']['children'])['data_binding_target_noun_id'] = dataFromModal.primary_Noun.id;
             	    ActivityInfo.associateNounData.primary_noun = dataFromModal.primary_Noun.id;
           	  }
           	  if(dataFromModal.pn_attribute){
           		 // find($scope.element_id, nodeTree['screen']['children'])['data_binding_target_noun_attr_id'] = dataFromModal.pn_attribute.id;
           		  ActivityInfo.associateNounData.pn_attribute = dataFromModal.pn_attribute.id;
           	  }
         	   var groupdata={
              			id: 0,
              			name:dataFromModal.name,
              			screenId: nodeTree['screen']['id'],
              			type:"radiobutton",
          				data_binding_context:"primary_noun",
             			noun_attribute_id: ActivityInfo.associateNounData.pn_attribute,
             			noun_id: ActivityInfo.associateNounData.primary_noun
              	};
          	  if(dataFromModal.name){
          		  // save groups in factory 
          		 console.log(",,,,,,,,,,,,,,,,,,,,,"+angular.toJson(ActivityInfo.radioArray.radiogroup.length));
          		  if(ActivityInfo.radioArray.radiogroup.length == 0){
          			 groupdata.id = 1;
          		  }else{
          			 for (i = 0; i < ActivityInfo.radioArray.radiogroup.length; i++) {
          		        if (ActivityInfo.radioArray.radiogroup[i].name === dataFromModal.name) {
          		        	var msg = "Group already Exist";
              	            $scope.openSaveDialog('sm', msg);
              	            return;
          		        }
          		    }
          			console.log("ActivityInfo.radioArray.radiogroup.length-1 "+ActivityInfo.radioArray.radiogroup[ActivityInfo.radioArray.radiogroup.length-1]);
          			groupdata.id = ActivityInfo.radioArray.radiogroup[ActivityInfo.radioArray.radiogroup.length-1].id+1;
          		  }
          		  ActivityInfo.radioArray.radiogroup.push(groupdata);         		  
          		  
          		 $scope.radiobuttongroups = ActivityInfo.radioArray.radiogroup;
          		 nodeTree['screen']['radioGroupList']= ActivityInfo.radioArray.radiogroup;
          			
          		  console.log(".........................."+angular.toJson(ActivityInfo.radioArray.radiogroup));
          	  }
            }, function () {
          	  console.log('Modal dismissed at: ' + new Date());
            });
         };

        $scope.defaultCheck = function(){
        	$scope.ischecked = $scope.checkboxModel.value;
        	//find($scope.element_id, nodeTree['screen']['children'])['isChecked'] = $scope.ischecked;
        	find($scope.element_id, nodeTree['screen']['children'])['checked'] = $scope.ischecked;
        	//alert(find($scope.element_id, nodeTree['screen']['children'])['checked']);
        }
        $scope.defaultRadioCheck = function(){
        	$scope.isRadiochecked = $scope.radioModel.value;
        	//find($scope.element_id, nodeTree['screen']['children'])['isChecked'] = $scope.isRadiochecked;
        	find($scope.element_id, nodeTree['screen']['children'])['checked'] = $scope.isRadiochecked;
        	//alert(find($scope.element_id, nodeTree['screen']['children'])['checked']);
        }
        $scope.addtoGroup = function(){
        	find($scope.element_id, nodeTree['screen']['children'])['group_values'] = "{group_id:"+$scope.selectedGroup.id+",group_name:"+$scope.selectedGroup.name+",isChecked:"+$scope.checkboxModel.value+"}";
        }
        
        $scope.addtoRadioGroup = function(){
        	find($scope.element_id, nodeTree['screen']['children'])['group_values'] = "{group_id:"+$scope.selectedRadioGroup.id+",group_name:"+$scope.selectedRadioGroup.name+",isChecked:"+$scope.radioModel.value+"}";
        }
}