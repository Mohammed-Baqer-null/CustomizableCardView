# CustomizableCardView

A customizable Material CardView library for Android with built-in icon, title, and summary support.

## Features

- Material Design 3 theming support
- Customizable icon with background
- Title and summary text
- Recommended badge
- Child content support
- Flexible layout options

## Installation

### Step 1: Add JitPack repository

**Kotlin DSL (settings.gradle.kts):**
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

**Groovy (settings.gradle):**
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add the dependency

**Kotlin DSL (build.gradle.kts):**
```kotlin
dependencies {
    implementation("com.github.Mohammed-baqer-null:CustomizableCardView:v1.0")
}
```

**Groovy (build.gradle):**
```groovy
dependencies {
    implementation 'com.github.Mohammed-baqer-null:CustomizableCardView:v1.0'
}
```

[![](https://jitpack.io/v/Mohammed-baqer-null/CustomizableCardView.svg)](https://jitpack.io/#Mohammed-baqer-null/CustomizableCardView)

## Basic Usage

```xml
<com.tom.customizablecardview.CustomizableCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:setCardIcon="@drawable/ic_settings"
    app:setCardTitle="Settings"
    app:setCardSummary="Configure your preferences" />
```

## Attributes

### Icon

| Attribute | Type | Description |
|-----------|------|-------------|
| `setCardIcon` | drawable | Icon resource |
| `setCardIconWidth` | dimension | Icon width |
| `setCardIconHeight` | dimension | Icon height |
| `setCardIconBackgroundColor` | color | Icon background color |
| `noIconBackground` | boolean | Hide icon background |
| `noIcon` | boolean | Hide icon completely |

### Text

| Attribute | Type | Description |
|-----------|------|-------------|
| `setCardTitle` | string | Title text |
| `setCardSummary` | string | Summary text |
| `setTitleTextSize` | dimension | Title text size |
| `setSummaryTextSize` | dimension | Summary text size |
| `setTitleFont` | string | Title font family |
| `setSummaryFont` | string | Summary font family |
| `noTitle` | boolean | Hide title |
| `noSummary` | boolean | Hide summary |

### Card Style

| Attribute | Type | Description |
|-----------|------|-------------|
| `setCardBackgroundColor` | color | Card background |
| `setCardElevation` | dimension | Card elevation |
| `setCardStroke` | dimension | Stroke width |
| `setCardStrokeColor` | color | Stroke color |

### Behavior

| Attribute | Type | Description |
|-----------|------|-------------|
| `isCheckableCard` | boolean | Enable checkable state |
| `isClickableCard` | boolean | Enable click |
| `isCardRecommended` | boolean | Show recommended badge |
| `contentAlign` | enum | Content alignment (left/center/right) |

## Programmatic Usage

```kotlin
val card = CustomizableCardView(context).apply {
    setCardIcon(R.drawable.ic_notification)
    setCardTitle("Notifications")
    setCardSummary("Manage your notifications")
    setCardIconBackgroundColor(Color.parseColor("#FF6B6B"))
    setRecommended(true)
}
```

## Adding Child Views

You can add custom views as children:

```kotlin
card.addView(myCustomView)
// or
card.getChildContentLayout().addView(myCustomView)
```

## Examples

### Simple card
```xml
<com.tom.customizablecardview.CustomizableCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:setCardIcon="@drawable/ic_home"
    app:setCardTitle="Home"
    app:setCardSummary="Back to home screen" />
```

### Card without icon background
```xml
<com.tom.customizablecardview.CustomizableCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:setCardIcon="@drawable/ic_profile"
    app:setCardTitle="Profile"
    app:noIconBackground="true" />
```

### Recommended card
```xml
<com.tom.customizablecardview.CustomizableCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:setCardIcon="@drawable/ic_premium"
    app:setCardTitle="Premium Features"
    app:isCardRecommended="true" />
```

## License

```
This library is licensed under the GNU Lesser General Public License v3.0.
See LICENSE file for details.
```

## Author

[Mohammed-baqer-null](https://github.com/Mohammed-baqer-null)
