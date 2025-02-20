# Version 4.5.0-beta (next version)

Due to a technical change, cached weather data will be cleared on update.

**Weather sources**
- Add support for HERE (@Cod3dDOT)
- Fix precipitation probability on OpenWeather (@Cod3dDOT)

**Data**
- Initial implementation of wind gusts on compatible weather sources. Display only in daily details at the moment.

**Animations**
- Reduce meteor spawning on clear night condition (@Cod3dDOT)
- Fix sensor stuttering (@Cod3dDOT)
- Fix Fog condition always showing Clear instead

**Other fixes**
- Fix contrast issue on Main screen Allergen card in some cases
- Fix database keeping old "current location" weather data, making app data size grow
- Fix overwrite of weather data when "current location" and a manually added city shared the same city and weather source (issue particularly noticeable with AccuWeather)


# Version 4.4.1-beta

- Add Allergens support for Open-Meteo source (Europe only at the moment). Allergens are different than AccuWeather ones (North America only).
- Uses universal scale for all allergens, regardless of weather sources
- New per-location settings dialog (will be improved in next versions)
- Allergens are now sorted by name
- Add support for Pirate Weather (thanks Cod3d.!)
- Fix crash on Android < 7.0
- Updated translations


# Version 4.4.0-beta

This version brings new logic that automatically completes missing data (for example, uses hourly data to extrapolate daily value for this data). This logic which was initially implemented per source is now transparently implemented for all sources. This means that even novice developers that want to add new sources will now be able to do it without having to care about that, making this task easier than ever.

If you notice any data that was available on v4.3.0-beta for a source and is now missing on v4.4.0-beta, please report it in a GitHub issue.

- When current details are missing from a source, app will now pick the closest hour forecast. Following this change, some sources will now have more current details.
- Sun & Moon & Moon phase is now available for all sources, it will be computed if data is missing from source.
- This fixes an issue with MET Norway where icons were always daytime on days 2+ due to missing sun info
- This also fixes many errors with midnight sun and polar night.
- Fix day and night temperature for OpenWeather which was completely broken and could show higher temperature at night than during the day.
- When dew point is missing and relative humidity and air temperature are available, it will be automatically computed.
- When degree day is missing, it will be calculated according to EU formula (check [Day details documentation](docs/DAY_DETAILS.md) for more info).
- When degree day is 0, it will no longer be shown.
- When temperature < 10 °C and wind speed > 4.8 km/h, and wind chill temperature is missing, it will be automatically computed.
- Fix top appbar/status bar visibility issues
- Make hourly trends less compressed.
- Add a “Help me choose” button on weather selection dialogs.
- Fix OpenWeather icons being always daytime.
- Fix alert list page was always scrolled to bottom
- When tapping on an alert (either from notification or from main screen), it will now jump to the top of this alert
- Fix cards no longer animated when entering screen
- Fix changing icon pack required a full restart of the app
- Revert swipe when trying to make the location list empty or cancelling a weather source update on current location.
- Remove clunky last daily forecast on Open-Meteo, MF, MET No, OpenWeather to avoid showing incomplete/incorrect data
- “Alerts to follow” message will no longer take you to past alerts, and alert list page will no longer show past alerts
- Fix contrast issue with next hour precipitation graph in light theme
- Fix search failure on Open-Meteo / GeoNames when country code was empty (Antarctica, for example)
- Updated translations.


# Version 4.3.0-beta

/!\ Custom API keys were reset in this version, following a move to a separate config store from the main app /!\

- [Regression fix] Sometimes, app would get stuck refreshing. It should no longer happen now.
- [Regression fix] If app fails to find current location, it will now refresh weather data for latest known position. In background, it will silently ignore error, while on main screen, it will show a snackbar to let user know that while it was refreshed, there was a problem finding current position.
- Add “start on boot” workaround for non-standard devices (such as MIUI) which didn’t have background workers resume after reboot
- Weather source for current location can now be chosen directly from the location list instead of going to settings, which was unintuitive and could be confusing.
- Add no network error on location search
- Throw error when trying to locate outside China when using Baidu IP location, instead of positioning on 0, 0
- Update resident feature to work with a 20 km radius instead of relying on 0.8 degrees.
- 中国 provider search now allows you to search for Chinese cities by its English name.
- [Technical] Many improvements to providers implementation for developers
- Added Kurdish Sorani translation (thanks anyone00!)
- Updated translations

# Version 4.2.1-beta

- Bring back detailed error (API key missing, API limit reached, no network, etc) on refresh failure instead of just “Weather failed”
- Added more errors (server timeout, failed to find location within reasonable time, parsing error, etc)
- Added detailed error for the location search as well
- Every setting change forced refreshing weather data, now it will only happen on language change or on current location provider change to avoid unnecessary refreshes
- Fix widgets were force refreshing weather data
- Fix issue when adding widgets
- Updated translations

# Version 4.2.0-beta

Background updates logic was entirely rewritten in this version.

- Refresh progress is now displayed in notification when weather update is happening
- If there are locations which failed to refresh in background, a notification will inform you and you will be able to access an error log for details (experimental).
- When having multiple locations, refreshes are done in parallel
- A preference was added to be able to ignore background refreshes when battery is low
- Today and tomorrow forecast notifications are now sent even if network is not available at requested time
- Fix alert notification logic being executed multiple times resulting in same alert being sent multiple times (this doesn’t fix duplicate alerts emitted as different alerts by AccuWeather).
- Fix app bar was not completely disappearing on main screen when scrolling fast
- Added Lithuanian translation (thanks Deividas Paukštė!)
- Updated translations


# Version 4.1.3-beta

- Add Nowcast for Nordic Area on MET No provider
- Add Air Quality for Norway on MET No provider
- Fix "Tap here to see alerts" not working
- Fix alert colors sometimes having low contrast
- Migrate to new sunrise API version for MET No, fix weather not being able to refresh when there were two moon rise during the same day
- Fix incorrect data being displayed in daily/hourly trends when data was unavailable (data from previous hours/days was being redrawn)
- Daily/Hourly trends will no longer show 0 when data is unavailable
- Fix lines being disconnected when weather icon is missing in daily/hourly trends
- Add "No direction" icon for wind on daily/hourly trends
- Keep moonrise/set and phase only for first day on Météo-France, instead of reusing data from first day on all days
- Fix precipitation talkback was always saying "No precipitation" in Daily precipitation trend
- Restore scroll behavior from Geometric Weather on main screen
- Fix display of refresh time on main screen on tablets
- Only display translators of the current language in settings
- Fix incorrect night temperature on OpenWeather and MF providers
- Fix Vietnamese language was not showing
- Added Bulgarian translation (thanks elgratea!)
- Updated translations

# Version 4.1.2-beta

- The following data can now handle decimals: temperatures (including all feels like temperature), dew point, UV index. Due to the technical nature of the change, existing cached data will be deleted on update and will be available again on next refresh.
- Alerts are now more visible and alert color is used when the weather provider supports it
- Add more info when tapping on air quality card or specific pollutant
- Cards now show on top right at what time they apply
- Make top bar small center aligned for location list, which fixes a stuttering issue at the same occasion
- Location search will now show advices if there are no search results instead of doing nothing
- No longer ask for notification permission (Android 13+) when adding location, show a dismissible info on location list instead
- Add additional settings from Android app info
- Fix Chronus icon packs not working
- Revert let Android 13 users revoke their permissions
- Translation updates

# Version 4.1.1-beta

- Location list is now empty on first install and app only requires location permissions if user wants to add its current location
- Add a dialog box for notification permission request
- Fix systematic crash on opening app on Android below R.
- Update Vietnamese translation.

# Version 4.1.0-beta

- Most of the code has been rewritten in Kotlin, which will make the app more reliable in the future.
- All notification channels are now declared on startup, so you can disable them before receiving at least one notification.
- Add precipitations in next hour for China provider.
- Fix current location not working for some providers.
- Fix crash in daily view when air quality is not available.
- Fix readability for Update method setting.
- Fix Beijing sometimes being added as location.
- Fix semi colon spacing in Hourly dialog.
- Fix Today sometimes not showing on the right column in notification-widget.
- Many null-safety checks added to avoid potential crashes, especially in widgets.
- Fix tap on a day on week widget didn’t open day detail if view style was 5 days.
- Fix don’t open location list when tapping on alerts.
- Fix first letter of weather text is not capitalized with OpenWeather provider.
- Fix wallpaper settings not opening in some cases.
- Fix tile not collapsing after being tapped on.
- Fix sensor was always queried regardless of gravity sensor preference.
- Fix weather data refresh when hourly temperature is partially available from API.
- Translation updates, Vietnamese added (thanks minb!).
- Support for weather data on cLock on CyanogenMod 5.0 was removed (unmaintained dependency, I’m not even sure it still worked).

# Version 4.0.5-alpha

- Add troubleshooting settings.
- Add more AccuWeather settings.
- Make widgets reconfigurable on recent Android versions.
- Update German, Italian and Portuguese (Brazil) translations.
- Fix location search results for AccuWeather (province was saved as city name, and city as district). Already existing locations must be re-added.
- Add small Celsius/Fahrenheit unit for temperature on main screen header.
- Fix back button in location list.
- Fix switch for Persistent notification widget
- Fix disabled settings could be switched on/off
- Fix could not re-add hourly air quality trend
- Last weather provider used for location search is now remembered.
- Don’t display Details/Live card if there is no data to display.
- Replace direction with arrow in wind short descriptions to help a bit in tight spaces.
- Various fixes and code improvements.

# Version 4.0.4-alpha

- Add current, hourly and daily air quality for Open-Meteo with credits.
- New settings organization and better wording. Some settings were reset to default.
- Fix daily trends and hourly trends configuration.
- Fix icon packs link.
- Fix some contrasts issues.

# Version 4.0.3-alpha

- Add current, hourly and daily air quality for AccuWeather.
- Add more detailed error messages.
- Fix icon color in light mode.
- Fix details not showing when all details were removed from header.

# Version 4.0.2-alpha

- Current details shown on header can now be configured in settings. Details not shown on header will display in the Details card.
- Fix refresh time not updating
- Update icons
- Make background immersive when app is loading
- Translation updates: Chinese (simplified), German

# Version 4.0.1-alpha

- Translation available from Weblate
- Make dividers thinner on homepage
- Fix vertical alignment of refresh time on homepage
- Fix monochrome icon
- Optimize new icons
- Require a restart when changing "Background animation" or "Gravity sensor" settings

# Version 4.0.0-alpha

Initial version of Breezy Weather fork

- New providers (Open-Meteo, MET Norway)
- Additional data for other providers
- New header design for homepage
- More Material 3 components
- Add hourly air quality
- Add Plume AQI scale for air quality widget
- Allow to disable background animation
- Documentation
- Translation updates thanks to contributors
- Tons of fixes
- Many non-visible improvements to the code