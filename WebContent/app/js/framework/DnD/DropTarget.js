function DropTarget(element) {
  element.dropTarget = this;

  this.canAccept = function (dragObject) {
    var drag, dragResizable, dragRole,
      drop, dropResizable, dropRole;

    dragResizable = dragObject.toString();
    drag = $('#' + dragResizable).children().not('span.resize-handle').attr('id');

    dropResizable = element.dropTarget.toString();
    drop = $('#' + dropResizable).children().not('span.resize-handle').attr('id') ?
           $('#' + dropResizable).children().not('span.resize-handle').attr('id') : dropResizable;

    if (dropResizable === 'screen') {
      drop = dropResizable;
    }

    dropRole = (drop == 'screen') ? 'screen' : $('#' + drop).data('role');
    dragRole = $('#' + drag).data('role');

    /* check for 3rd level containers */
   /* switch (dropRole) {
      case 'border-container':
      case 'card':
      case 'panel':
      case 'tab':
      case 'accordion':
        switch (dragRole) {
          case 'border-container':
          case 'card':
          case 'panel':
          case 'tab':
          case 'accordion':
             //store values in not_allowed.third_level to check in accept() 
            window.not_allowed.third_level.drop = drop;
            window.not_allowed.third_level.drag = drag;
            return false;
        }

        break;
    }*/

    return true;
  };

  this.accept = function (dragObject) {
    this.onLeave();

    console.log("'" + this + "': accepted '" + dragObject + "'");

    var screenChildren = nodeTree['screen']['children'];
    var widgetIndex = AxeControllerScope.widgetIndex[++AxeControllerScope.widgetCount];

    var dragResizable = dragObject.toString();
    var drag = $('#' + dragResizable).children().not('span.resize-handle').attr('id');
    var dropResizable = element.dropTarget.toString();
    var drop = ($('#' + dropResizable).children().not('span.resize-handle').attr('id')) ?
                $('#' + dropResizable).children().not('span.resize-handle').attr('id') : dropResizable;

    if (dropResizable === 'screen') {
      drop = dropResizable;
    }

    var dragParent, dropParent, dragParentResizable;

    /* 3rd level containers are not allowed */
    /*if (drag != window.not_allowed.third_level.warned) {
      window.not_allowed.third_level.warned = false;
    }

    if (window.not_allowed.third_level.drop || window.not_allowed.third_level.drag) {
      if (!window.not_allowed.third_level.warned) {
        alert('Third level containers are not allowed.');
        window.not_allowed.third_level.warned = window.not_allowed.third_level.drag;
      }
       //clear not_allowed.third_level values 
      window.not_allowed.third_level.drop = null;
      window.not_allowed.third_level.drag = null;
    }*/

    /* Add drag object to nodeTree */
    if (!find(dragResizable, screenChildren)) {
      screenChildren[dragResizable] = {
        id: 0,
        type: $('#' + drag).data('role'),
        name: '',
        label: '',
        description: '',
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
      };

      switch ($('#' + drag).data('role')) {
        case 'label':
          screenChildren[dragResizable]['label'] = 'Label';
          screenChildren[dragResizable]['name'] = 'Label_' + widgetIndex;

          break;
        case 'button':
          screenChildren[dragResizable]['label'] = 'Button';
          screenChildren[dragResizable]['name'] = 'Button_' + widgetIndex;

          break;
        case 'camera':
          screenChildren[dragResizable]['label'] = 'Take Photos';
          screenChildren[dragResizable]['name'] = 'Camera_' + widgetIndex;

          break;
        case 'recorder':
            screenChildren[dragResizable]['label'] = 'Record Sound';
            screenChildren[dragResizable]['name'] = 'SoundRecorder_' + widgetIndex;
            break; 
        case 'video':
            screenChildren[dragResizable]['label'] = 'Record Video';
            screenChildren[dragResizable]['name'] = 'VideoRecorder_' + widgetIndex;
            break;     
        /* Boopathi */
        case 'checkbox':
          screenChildren[dragResizable]['label'] = 'Checkbox Label';
          screenChildren[dragResizable]['name'] = 'Checkbox_' + widgetIndex;

          break;
        /* Boopathi */
        case 'radio':
          screenChildren[dragResizable]['label'] = 'Radio';
          screenChildren[dragResizable]['name'] = 'Radio_' + widgetIndex;
          screenChildren[dragResizable]['is_container'] = false;
          screenChildren[dragResizable]['columns'] = [];
          $('#' + drag).find('label').each(function (index, node) {
            screenChildren[dragResizable]['columns'].push({
              id: 0,
              type: 'radio',
              name: 'Radio_' + AxeControllerScope.widgetIndex[++AxeControllerScope.widgetCount], /* for unique name */
              description: '',
              label: $(node).find('div').text() + '',
              notes: '',
              parent_id: ~~dragResizable,
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
          });

          break;
        case 'table':
          screenChildren[dragResizable]['name'] = 'Table_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Table';
          screenChildren[dragResizable]['is_container'] = true;
          screenChildren[dragResizable]['rows'] = $('#' + drag).find('tr').length;
          screenChildren[dragResizable]['columns'] = [];
          var tableIndex = 0;
          $('#' + drag + ' thead').find('th').each(function (index, node) {
            screenChildren[dragResizable]['columns'].push({
              id: 0,
              type: 'column',
              name: 'Column_' + AxeControllerScope.widgetIndex[++AxeControllerScope.widgetCount], /* for unique name */
              label: $(node).text() + '',
              description: '',
              notes: '',
              parent_id: 0,
              parent_name: '',
              screen_id: nodeTree['screen']['id'],
              number_of_children: 0,
              supports_label: '',
              ui_technology: '',
              is_container: false,
              data_binding_context: 'not_bound',
              noun_id: 0,
              noun_attribute_id: 0,
              extended_attributes: '',
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
              landscape_offset_y: 0,
              section_position: ++tableIndex
            });
          });

          break;
        /* Boopathi */
        case 'list':
          screenChildren[dragResizable]['name'] = 'List_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'List';
          screenChildren[dragResizable]['is_container'] = true;
          screenChildren[dragResizable]['columns'] = [];
          $('#' + drag).find('li').each(function (index, node) {
            screenChildren[dragResizable]['columns'].push({
              id: 0,
              type: 'list_item',
              name: 'list_' + AxeControllerScope.widgetIndex[++AxeControllerScope.widgetCount], /* for unique name */
              description: '',
              label: $(node).text() + '',
              notes: '',
              parent_id: ~~dragResizable,
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
          });

          break;
        case 'select':
            screenChildren[dragResizable]['name'] = 'Select_' + widgetIndex;
            screenChildren[dragResizable]['label'] = 'Select';

            break;

        /* Boopathi */
        case 'link':
          screenChildren[dragResizable]['name'] = 'Link_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Link';

          break;
        case 'range':
          screenChildren[dragResizable]['name'] = 'Range_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Range';

          break;
        case 'input':
          screenChildren[dragResizable]['name'] = 'Input_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Input';

          break;
        case 'toggle':
          screenChildren[dragResizable]['name'] = 'Toggle_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Toggle';

          break;
        case 'header':
          screenChildren[dragResizable]['name'] = 'Header_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Header';

          break;
        case 'footer':
          screenChildren[dragResizable]['name'] = 'Footer_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Footer';

          break;
        case 'date':
          screenChildren[dragResizable]['name'] = 'Date_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Date';

          break;
        case 'select':
          screenChildren[dragResizable]['name'] = 'Select_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Select';

          break;
        case 'hrule':
          screenChildren[dragResizable]['name'] = 'Hrule_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Hrule';

          break;
        case 'vrule':
          screenChildren[dragResizable]['name'] = 'Vrule_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Vrule';

          break;
        case 'rich-text-editor':
          screenChildren[dragResizable]['name'] = 'Rich_text_editor_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Rich_text_editor';

          break;
        case 'image':
          screenChildren[dragResizable]['name'] = 'Image' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Image';
          screenChildren[dragResizable]['image_src'] = 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCEtLSBHZW5lcmF0ZWQgYnkgSWNvTW9vbi5pbyAtLT4KPCFET0NUWVBFIHN2ZyBQVUJMSUMgIi0vL1czQy8vRFREIFNWRyAxLjEvL0VOIiAiaHR0cDovL3d3dy53My5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkIj4KPHN2ZyB2ZXJzaW9uPSIxLjEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHdpZHRoPSIxOCIgaGVpZ2h0PSIxOCIgdmlld0JveD0iMCAwIDE2IDE2Ij4KPGc+Cgk8Zz4KCQk8cGF0aCBzdHlsZT0iIiBkPSJNMCwydjEyaDE2VjJIMHogTTE1LDEzSDFWM2gxNFYxM3oiIGZpbGw9IiI+PC9wYXRoPgoJCTxjaXJjbGUgc3R5bGU9IiIgY3g9IjEyLjUiIGN5PSI1LjUiIHI9IjEuNSIgZmlsbD0iIj48L2NpcmNsZT4KCQk8cGF0aCBzdHlsZT0iIiBkPSJNMTAuMTExLDguMDIxYy0wLjkwOSwwLTAuODE1LDEuOTM2LTIuMDYsMS45MzZjLTEuMjQyLDAtMS45MTgtNC45NTMtMy40NjMtNC45NTMgICAgYy0xLjU0NCwwLTIuNTg0LDcuMDIxLTIuNTg0LDcuMDIxSDE0LjE1QzE0LjE1LDEyLjAyNSwxMS4wMjEsOC4wMjEsMTAuMTExLDguMDIxeiIgZmlsbD0iIj48L3BhdGg+Cgk8L2c+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPC9zdmc+';

          break;
        
        case 'map':
            screenChildren[dragResizable]['name'] = 'Map' + widgetIndex;
            screenChildren[dragResizable]['label'] = 'map';
            screenChildren[dragResizable]['map-image'] = 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCEtLSBHZW5lcmF0ZWQgYnkgSWNvTW9vbi5pbyAtLT4KPCFET0NUWVBFIHN2ZyBQVUJMSUMgIi0vL1czQy8vRFREIFNWRyAxLjEvL0VOIiAiaHR0cDovL3d3dy53My5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkIj4KPHN2ZyB2ZXJzaW9uPSIxLjEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHdpZHRoPSIxOCIgaGVpZ2h0PSIxOCIgdmlld0JveD0iMCAwIDE2IDE2Ij4KPGc+Cgk8Zz4KCQk8cGF0aCBzdHlsZT0iIiBkPSJNMCwydjEyaDE2VjJIMHogTTE1LDEzSDFWM2gxNFYxM3oiIGZpbGw9IiI+PC9wYXRoPgoJCTxjaXJjbGUgc3R5bGU9IiIgY3g9IjEyLjUiIGN5PSI1LjUiIHI9IjEuNSIgZmlsbD0iIj48L2NpcmNsZT4KCQk8cGF0aCBzdHlsZT0iIiBkPSJNMTAuMTExLDguMDIxYy0wLjkwOSwwLTAuODE1LDEuOTM2LTIuMDYsMS45MzZjLTEuMjQyLDAtMS45MTgtNC45NTMtMy40NjMtNC45NTMgICAgYy0xLjU0NCwwLTIuNTg0LDcuMDIxLTIuNTg0LDcuMDIxSDE0LjE1QzE0LjE1LDEyLjAyNSwxMS4wMjEsOC4wMjEsMTAuMTExLDguMDIxeiIgZmlsbD0iIj48L3BhdGg+Cgk8L2c+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPC9zdmc+';
            screenChildren[dragResizable]['map-input'] = 'Chennai, IN';
            break;
      }

      /* Make drop targets from drag object */
      switch ($('#' + drag).data('role')) {
        case 'border-container':
          screenChildren[dragResizable]['name'] = 'Border_container_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Border_container';
          screenChildren[dragResizable]['is_container'] = true;
          screenChildren[dragResizable]['children'] = {};
          new DropTarget($('#' + dragResizable)[0]);

          break;
        case 'tab':
          screenChildren[dragResizable]['name'] = 'Tab_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Tab';
          screenChildren[dragResizable]['is_container'] = true;
          screenChildren[dragResizable]['children'] = {};
          /* Boopathi */
          var tabIndex = 0;
          $('#' + dragResizable).find('.section').each(function (index, panel) {
            screenChildren[dragResizable]['children'][$(panel).attr('id')] = {
              id: 0,
              type: $(panel).data('role'),
              name: 'Tab_' + AxeControllerScope.widgetIndex[++AxeControllerScope.widgetCount], /* for unique name */
              description: '',
              label: 'Tab ' + (++tabIndex), /* add label for Tab - not able to get tab header text*/
              notes: '',
              children: {},
              parent_id: ~~dragResizable,
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
              section_position: tabIndex
            };
            new DropTarget(panel);
          });

          break;
        case 'accordion':
          screenChildren[dragResizable]['name'] = 'Accordion_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Accordion';
          screenChildren[dragResizable]['is_container'] = true;
          screenChildren[dragResizable]['children'] = {};
          /* Boopathi */
          var panelIndex = 0;
          $('#' + dragResizable).find('.section').each(function (index, panel) {
            screenChildren[dragResizable]['children'][$(panel).attr('id')] = {
              id: 0,
              type: $(panel).data('role'),
              name: 'Item_' + AxeControllerScope.widgetIndex[++AxeControllerScope.widgetCount], /* for unique name */
              description: '',
              label: 'Item #' + (++panelIndex), /* add label for panel - not able to get panel header text*/
              notes: '',
              children: {},
              parent_id: ~~dragResizable,
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
              portrait_width: 300,
              portrait_height: 100,
              section_position: panelIndex
            };
            new DropTarget(panel);
          });

          break;
        case 'panel':
          screenChildren[dragResizable]['name'] = 'Panel_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Panel';
          screenChildren[dragResizable]['is_container'] = true;
          screenChildren[dragResizable]['children'] = {};
          new DropTarget($('#' + dragResizable).find('.panel-body')[0]);

          break;
        case 'card':
          /* Boopathi */
          screenChildren[dragResizable]['name'] = 'Card_' + widgetIndex;
          screenChildren[dragResizable]['label'] = 'Card';
          screenChildren[dragResizable]['header'] = 'Header';
          screenChildren[dragResizable]['footer'] = 'Footer';

          screenChildren[dragResizable]['is_container'] = true;
          screenChildren[dragResizable]['children'] = {};
          new DropTarget($('#' + dragResizable).find('.item-text-wrap')[0]);

          break;
      }
    }

    if (drop === 'screen') {
      /* Keep drag object old parent */
      dragParent = find(dragResizable, screenChildren)['parent_id'];

      /* Drag from another container and drop into screen */
      if (~~dragParent !== 0) {
        /* Set drag object parent to screen */
        find(dragResizable, screenChildren)['parent_id'] = 0;

        /* Add drag object to screen children */
        screenChildren[dragResizable] = $.extend(true, {}, find(dragResizable, screenChildren));

        /* reset portraitOffset and landscapeOffset properties */
        screenChildren[dragResizable]['portraitOffsetX'] = 0;
        screenChildren[dragResizable]['portraitOffsetY'] = 0;
        screenChildren[dragResizable]['landscapeOffsetX'] = 0;
        screenChildren[dragResizable]['landscapeOffsetY'] = 0;

        /* Remove drag object from old parent children */
        if (screenChildren[dragParent]) {
          delete screenChildren[dragParent]['children'][dragResizable];
        } else if (screenChildren[find(dragParent, screenChildren)['parent_id']]) {
          delete screenChildren[find(dragParent, screenChildren)['parent_id']]['children'][dragParent]['children'][dragResizable];
        }
      }
    }

/*    if ($('#' + drop).data('role') === 'border-container') {
       Keep drag object old parent 
      dragParent = find(dragResizable, screenChildren)['parent_id'];
      dropParent = find(drop, screenChildren)['parent_id'];
      dragParentResizable = $('#' + dragParent).parents('.resizable').attr('id');

       Set drag object parent to current border container 
      find(dragResizable, screenChildren)['parent_id'] = ~~dropResizable;

       Add drag object to current border container children 
      screenChildren[dropResizable]['children'][dragResizable] = $.extend(true, {}, find(dragResizable, screenChildren));

       Add portraitOffset and landscapeOffset properties to drag object 
      temp = screenChildren[dropResizable]['children'][dragResizable];
      temp[orientation + 'OffsetX'] = Math.round($('#' + dragResizable).offset().left - $('#' + dropResizable).offset().left);
      temp[orientation + 'OffsetY'] = Math.round($('#' + dragResizable).offset().top - $('#' + dropResizable).offset().top);

      if (!temp['portraitOffsetX'] && !temp['portraitOffsetY']) {
        temp['portraitOffsetX'] = temp['landscapeOffsetX'];
        temp['portraitOffsetY'] = temp['landscapeOffsetY'];
      }

      if (!temp['landscapeOffsetX'] && !temp['landscapeOffsetY']) {
        temp['landscapeOffsetX'] = temp['portraitOffsetX'];
        temp['landscapeOffsetY'] = temp['portraitOffsetY'];
      }

      delete temp;

       Remove drag object from old parent children 
      if (~~dragParent !== ~~dropResizable) {
        if (screenChildren[dragResizable]) {
          delete screenChildren[dragResizable];
        } else if (screenChildren[dragParent]) {
          delete screenChildren[dragParent]['children'][dragResizable];
        } else if (screenChildren[dragParentResizable]) {
          delete screenChildren[dragParentResizable]['children'][dragParent]['children'][dragResizable];
        }
      }

       Change border container z-index 
      if ($('#' + dropResizable).css('z-index') === 'auto') {
        $('#' + dropResizable).css('z-index', index);
        ++index;
      }
       Change drag object z-index 
      if ($('#' + dragResizable).css('z-index') === 'auto' || ($('#' + dragResizable).css('z-index') <= $('#' + dropResizable).css('z-index'))) {
        $('#' + dragResizable).css('z-index', index);
        ++index;
      }

       Boopathi 
       change children object z-index 
      var childrenKeys = Object.keys(screenChildren[dropResizable]['children']);
      for (var i = 0; i < childrenKeys.length; i++) {
        if ($('#' + childrenKeys[i]).css('z-index') === 'auto') {
          $('#' + childrenKeys[i]).css('z-index', index);
          ++index;
        }
      }
    }*/

    if ($('#' + drop).data('role') === 'section' && $('#' + drop).parents('._Gp_element').data('role') === 'tab') {
      dropResizable = $('#' + drop).parents('.resizable').attr('id');

      /* Keep drag object old parent */
      dragParent = find(dragResizable, screenChildren)['parent_id'];
      dragParentResizable = $('#' + dragParent).parents('.resizable').attr('id');
      dropParent = find(drop, screenChildren)['parent_id'];

      /* Set drag object parent to current tab panel */
      find(dragResizable, screenChildren)['parent_id'] = ~~drop;

      /* Add drag object to current tab panel children */
      screenChildren[dropResizable]['children'][drop]['children'][dragResizable] = $.extend(true, {}, find(dragResizable, screenChildren));

      /* Add portraitOffset and landscapeOffset properties to drag object */
      temp = screenChildren[dropResizable]['children'][drop]['children'][dragResizable];
      temp[orientation + 'OffsetX'] = Math.round($('#' + dragResizable).offset().left - $('#' + drop).offset().left + $('#' + drop).position().left);
      temp[orientation + 'OffsetY'] = Math.round($('#' + dragResizable).offset().top - $('#' + drop).offset().top + $('#' + drop).position().top);

      if (!temp['portraitOffsetX'] && !temp['portraitOffsetY']) {
        temp['portraitOffsetX'] = temp['landscapeOffsetX'];
        temp['portraitOffsetY'] = temp['landscapeOffsetY'];
      }

      if (!temp['landscapeOffsetX'] && !temp['landscapeOffsetY']) {
        temp['landscapeOffsetX'] = temp['portraitOffsetX'];
        temp['landscapeOffsetY'] = temp['portraitOffsetY'];
      }

      delete temp;

      /* Remove drag object from old parent children */
      if (~~dragParent !== ~~drop) {
        if (screenChildren[dragResizable]) {
          delete screenChildren[dragResizable];
        } else if (screenChildren[dragParent]) {
          delete screenChildren[dragParent]['children'][dragResizable];
        } else if (screenChildren[dragParentResizable]) {
          delete screenChildren[dragParentResizable]['children'][dragParent]['children'][dragResizable];
        }
      }

      /* Change tab z-index */
      if ($('#' + dropResizable).css('z-index') === 'auto') {
        $('#' + dropResizable).css('z-index', index);
        ++index;
      }

      /* Change drag object z-index */
      if ($('#' + dragResizable).css('z-index') === 'auto' || ($('#' + dragResizable).css('z-index') <= $('#' + dropResizable).css('z-index'))) {
        $('#' + dragResizable).css('z-index', index);
        ++index;
      }

      /* Boopathi */
      /* change children object z-index */

      /* find drop re-sizable children //section */
      var dropResizableChildern = Object.keys(screenChildren[dropResizable]['children']);
      for (var j = 0; j < dropResizableChildern.length; j++) {
        /* find drop children //component's inside the section */
        var dropChildrenKeys = Object.keys(find(dropResizableChildern[j], screenChildren[dropResizable]['children'])['children']);
        for (var i = 0; i < dropChildrenKeys.length; i++) {
          /* set z-index value for drop children */
          if ($('#' + dropChildrenKeys[i]).css('z-index') === 'auto') {
            $('#' + dropChildrenKeys[i]).css('z-index', index);
            ++index;
          }
        }
      }
    }

    if ($('#' + drop).data('role') === 'section' && $('#' + drop).parents('._Gp_element').data('role') === 'accordion') {
      dropResizable = $('#' + drop).parents('.resizable').attr('id');

      /* Keep drag object old parent */
      dragParent = find(dragResizable, screenChildren)['parent_id'];
      dragParentResizable = $('#' + dragParent).parents('.resizable').attr('id');
      dropParent = find(drop, screenChildren)['parent_id'];

      /* Set drag object parent to current accordion panel */
      find(dragResizable, screenChildren)['parent_id'] = ~~drop;

      /* Add drag object to current accordion panel */
      screenChildren[dropResizable]['children'][drop]['children'][dragResizable] = $.extend(true, {}, find(dragResizable, screenChildren));

      /* Add portraitOffset and landscapeOffset properties to drag object */
      temp = screenChildren[dropResizable]['children'][drop]['children'][dragResizable];
      temp[orientation + 'OffsetX'] = Math.round($('#' + dragResizable).offset().left - $('#' + drop).offset().left + $('#' + drop).position().left);
      temp[orientation + 'OffsetY'] = Math.round($('#' + dragResizable).offset().top - $('#' + drop).offset().top + $('#' + drop).position().top);

      if (!temp['portraitOffsetX'] && !temp['portraitOffsetY']) {
        temp['portraitOffsetX'] = temp['landscapeOffsetX'];
        temp['portraitOffsetY'] = temp['landscapeOffsetY'];
      }

      if (!temp['landscapeOffsetX'] && !temp['landscapeOffsetY']) {
        temp['landscapeOffsetX'] = temp['portraitOffsetX'];
        temp['landscapeOffsetY'] = temp['portraitOffsetY'];
      }

      delete temp;

      /* Remove drag object from old parent children */
      if (~~dragParent !== ~~drop) {
        if (screenChildren[dragResizable]) {
          delete screenChildren[dragResizable];
        } else if (screenChildren[dragParent]) {
          delete screenChildren[dragParent]['children'][dragResizable];
        } else if (screenChildren[dragParentResizable]) {
          delete screenChildren[dragParentResizable]['children'][dragParent]['children'][dragResizable];
        }
      }

      /* Change accordion z-index */
      if ($('#' + dropResizable).css('z-index') === 'auto') {
        $('#' + dropResizable).css('z-index', index);
        ++index;
      }

      /* Change drag object z-index */
      if ($('#' + dragResizable).css('z-index') === 'auto' || ($('#' + dragResizable).css('z-index') <= $('#' + dropResizable).css('z-index'))) {
        $('#' + dragResizable).css('z-index', index);
        ++index;
      }

      /* Boopathi */
      /* change children object z-index */

      /* find drop re-sizable children //section */
      var dropResizableChildern = Object.keys(screenChildren[dropResizable]['children']);
      for (var j = 0; j < dropResizableChildern.length; j++) {
        /* find drop children //component's inside the section */
        var dropChildrenKeys = Object.keys(find(dropResizableChildern[j], screenChildren[dropResizable]['children'])['children']);
        for (var i = 0; i < dropChildrenKeys.length; i++) {
          /* set z-index value for drop children */
          if ($('#' + dropChildrenKeys[i]).css('z-index') === 'auto') {
            $('#' + dropChildrenKeys[i]).css('z-index', index);
            ++index;
          }
        }
      }
    }

    if ($('#' + drop).data('role') === 'panel-content') {
      dropResizable = $('#' + drop).parents('.resizable').attr('id');

      /* keep drag object's old parent */
      dragParent = find(dragResizable, screenChildren)['parent_id'];
      dragParentResizable = $('#' + dragParent).parents('.resizable').attr('id');
      dropParent = find(drop, screenChildren)['parent_id'];

      /* Set drag object parent to current panel */
      find(dragResizable, screenChildren)['parent_id'] = ~~dropResizable;

      /* Add drag object to current panel children */
      screenChildren[dropResizable]['children'][dragResizable] = $.extend(true, {}, find(dragResizable, screenChildren));

      /* Add portraitOffset and landscapeOffset properties to drag object */
      temp = screenChildren[dropResizable]['children'][dragResizable];
      temp[orientation + 'OffsetX'] = Math.round($('#' + dragResizable).offset().left - $('#' + dropResizable).offset().left);
      temp[orientation + 'OffsetY'] = Math.round($('#' + dragResizable).offset().top - $('#' + dropResizable).offset().top);

      if (!temp['portraitOffsetX'] && !temp['portraitOffsetY']) {
        temp['portraitOffsetX'] = temp['landscapeOffsetX'];
        temp['portraitOffsetY'] = temp['landscapeOffsetY'];
      }

      if (!temp['landscapeOffsetX'] && !temp['landscapeOffsetY']) {
        temp['landscapeOffsetX'] = temp['portraitOffsetX'];
        temp['landscapeOffsetY'] = temp['portraitOffsetY'];
      }

      delete temp;

      /* Remove drag object from old parent children */
      if (~~dragParent !== ~~dropResizable) {
        if (screenChildren[dragResizable]) {
          delete screenChildren[dragResizable];
        } else if (screenChildren[dragParent]) {
          delete screenChildren[dragParent]['children'][dragResizable];
        } else if (screenChildren[dragParentResizable]) {
          delete screenChildren[dragParentResizable]['children'][dragParent]['children'][dragResizable];
        }
      }

      /* Change panel's z-index */
      if ($('#' + dropResizable).css('z-index') === 'auto') {
        $('#' + dropResizable).css('z-index', index);
        ++index;
      }

      /* Change drag object's z-index */
      if ($('#' + dragResizable).css('z-index') === 'auto' || ($('#' + dragResizable).css('z-index') <= $('#' + dropResizable).css('z-index'))) {
        $('#' + dragResizable).css('z-index', index);
        ++index;
      }

      /* Boopathi */
      /* change children object z-index */
      var childrenKeys = Object.keys(screenChildren[dropResizable]['children']);
      for (var i = 0; i < childrenKeys.length; i++) {
        if ($('#' + childrenKeys[i]).css('z-index') === 'auto') {
          $('#' + childrenKeys[i]).css('z-index', index);
          ++index;
        }
      }
    }

    //for border container
    
    if ($('#' + drop).data('role') === 'border-container') {
        dropResizable = $('#' + drop).parents('.resizable').attr('id');

        /* keep drag object's old parent */
        dragParent = find(dragResizable, screenChildren)['parent_id'];
        dragParentResizable = $('#' + dragParent).parents('.resizable').attr('id');
        dropParent = find(drop, screenChildren)['parent_id'];

        /* Set drag object parent to current panel */
        find(dragResizable, screenChildren)['parent_id'] = ~~dropResizable;

        /* Add drag object to current panel children */
        screenChildren[dropResizable]['children'][dragResizable] = $.extend(true, {}, find(dragResizable, screenChildren));

        /* Add portraitOffset and landscapeOffset properties to drag object */
        temp = screenChildren[dropResizable]['children'][dragResizable];
        temp[orientation + 'OffsetX'] = Math.round($('#' + dragResizable).offset().left - $('#' + dropResizable).offset().left);
        temp[orientation + 'OffsetY'] = Math.round($('#' + dragResizable).offset().top - $('#' + dropResizable).offset().top);

        if (!temp['portraitOffsetX'] && !temp['portraitOffsetY']) {
          temp['portraitOffsetX'] = temp['landscapeOffsetX'];
          temp['portraitOffsetY'] = temp['landscapeOffsetY'];
        }

        if (!temp['landscapeOffsetX'] && !temp['landscapeOffsetY']) {
          temp['landscapeOffsetX'] = temp['portraitOffsetX'];
          temp['landscapeOffsetY'] = temp['portraitOffsetY'];
        }

        delete temp;

        /* Remove drag object from old parent children */
        if (~~dragParent !== ~~dropResizable) {
          if (screenChildren[dragResizable]) {
            delete screenChildren[dragResizable];
          } else if (screenChildren[dragParent]) {
            delete screenChildren[dragParent]['children'][dragResizable];
          } else if (screenChildren[dragParentResizable]) {
            delete screenChildren[dragParentResizable]['children'][dragParent]['children'][dragResizable];
          }
        }

        /* Change panel's z-index */
        if ($('#' + dropResizable).css('z-index') === 'auto') {
          $('#' + dropResizable).css('z-index', index);
          ++index;
        }

        /* Change drag object's z-index */
        if ($('#' + dragResizable).css('z-index') === 'auto' || ($('#' + dragResizable).css('z-index') <= $('#' + dropResizable).css('z-index'))) {
          $('#' + dragResizable).css('z-index', index);
          ++index;
        }

        /* Boopathi */
        /* change children object z-index */
        var childrenKeys = Object.keys(screenChildren[dropResizable]['children']);
        for (var i = 0; i < childrenKeys.length; i++) {
          if ($('#' + childrenKeys[i]).css('z-index') === 'auto') {
            $('#' + childrenKeys[i]).css('z-index', index);
            ++index;
          }
        }
      }

    
    
    
    if ($('#' + drop).data('role') === 'card-content') {
      dropResizable = $('#' + drop).parents('.resizable').attr('id');

      /* keep drag object's old parent */
      dragParent = find(dragResizable, screenChildren)['parent_id'];
      dragParentResizable = $('#' + dragParent).parents('.resizable').attr('id');
      dropParent = find(drop, screenChildren)['parent_id'];

      /* set drag object parent to current card */
      find(dragResizable, screenChildren)['parent_id'] = ~~dropResizable;

      /* add drag object to current card children */
      screenChildren[dropResizable]['children'][dragResizable] = $.extend(true, {}, find(dragResizable, screenChildren));

      /* add portraitOffset and landscapeOffset properties to drag object */
      temp = screenChildren[dropResizable]['children'][dragResizable];
      temp[orientation + 'OffsetX'] = Math.round($('#' + dragResizable).offset().left - $('#' + dropResizable).offset().left);
      temp[orientation + 'OffsetY'] = Math.round($('#' + dragResizable).offset().top - $('#' + dropResizable).offset().top);

      if (!temp['portraitOffsetX'] && !temp['portraitOffsetY']) {
        temp['portraitOffsetX'] = temp['landscapeOffsetX'];
        temp['portraitOffsetY'] = temp['landscapeOffsetY'];
      }

      if (!temp['landscapeOffsetX'] && !temp['landscapeOffsetY']) {
        temp['landscapeOffsetX'] = temp['portraitOffsetX'];
        temp['landscapeOffsetY'] = temp['portraitOffsetY'];
      }

      delete temp;

      /* remove drag object from old parent children */
      if (~~dragParent !== ~~dropResizable) {
        if (screenChildren[dragResizable]) {
          delete screenChildren[dragResizable];
        } else if (screenChildren[dragParent]) {
          delete screenChildren[dragParent]['children'][dragResizable];
        } else if (screenChildren[dragParentResizable]) {
          delete screenChildren[dragParentResizable]['children'][dragParent]['children'][dragResizable];
        }
      }

      /* change card's z-index */
      if ($('#' + dropResizable).css('z-index') === 'auto') {
        $('#' + dropResizable).css('z-index', index);
        ++index;
      }

      /* change drag object's z-index */
      if ($('#' + dragResizable).css('z-index') === 'auto' || ($('#' + dragResizable).css('z-index') <= $('#' + dropResizable).css('z-index'))) {
        $('#' + dragResizable).css('z-index', index);
        ++index;
      }

      /* Boopathi */
      /* change children object z-index */
      var childrenKeys = Object.keys(screenChildren[dropResizable]['children']);
      for (var i = 0; i < childrenKeys.length; i++) {
        if ($('#' + childrenKeys[i]).css('z-index') === 'auto') {
          $('#' + childrenKeys[i]).css('z-index', index);
          ++index;
        }
      }
    }

    console.log(nodeTree);
  };

  this.onLeave = function () {
  };
  this.onEnter = function () {
  };

  this.toString = function () {
    return element.id;
  };
}