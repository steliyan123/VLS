
Project Name
======================
Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro Some Intro 

## Table of contents

- [Project Contents](#project-contents)
    - [Description](#description)
    - [Repository Owners and Substitutes](#repository-owners-and-substitutes)
- [Dependencies and Tools](#dependencies-and-tools)
    - [Libraries](#libraries)
    - [Tools](#tools)
- [Branches](#branches)
- [Documentation](#documentation)
- [Structure](#structure)
- [Links](#links)
- [Extras](#extras)
	- [UML Diagrams](#uml-diagrams)


##Project Contents

Project Contents Project Contents Project Contents Project Contents Project Contents Project Contents Project Contents Project Contents Project Contents Project Contents Project Contents Project Contents Project Contents 

### Description

Project Description Project Description Project Description Project Description Project Description Project Description Project Description Project Description Project Description Project Description Project Description 


### Repository Owners and Substitutes

 Repository Owners and Substitutes Repository Owners and Substitutes Repository Owners and Substitutes Repository Owners and Substitutes Repository Owners and Substitutes Repository Owners and Substitutes Repository Owners and Substitutes Repository Owners and Substitutes

##Dependencies and Tools
Some Text Some Text Some Text Some Text Some Text Some Text Some Text 

### Libraries
* Lib1
* Lib2
* lIB3

### Tools
* Tool1
* Tool2
* Tool3

##Branches

| Branch   | Purpose   | Usage   |
| :------- | --------: | :-----: |
| Branch 1 | Feature 1 | Testing |
| Branch 2 | Feature 2 | Testing |
| Branch 3 | Feature 3 | Demo    |

##Flavors

| Flavor   | Purpose   | Usage   |
| :------- | --------: | :-----: |
| Flavor 1 | Feature 1 | Testing |
| Flavor 2 | Feature 2 | Testing |
| Flavor 3 | Feature 3 | Demo    |

##Customers

| Customer| Column 2 | Column 3 |
| :------ | -------: | :------: |
| Audi 	  | Item 1   |  Item    |
| Porsche | Item 2   |  Item    |
| Bentley | Item 3   |  Item    |

##Products

| Product  | Column 2 | Column 3 |
| :------  | -------: | :------: |
| SDIS1    | Item 1   | Item     |
| SDIS2    | Item 2   | Item     |
| SCON     | Item 3   | Item     |

## Documentation
Technical Documentation Technical Documentation Technical Documentation Technical Documentation Technical Documentation Technical Documentation Technical Documentation Technical Documentation 

## Structure
 Internal Structure Internal Structure Internal Structure Internal Structure Internal Structure Internal Structure Internal Structure Internal Structure Internal Structure Internal Structure Internal Structure Internal Structure 

## Links

* [Web site](https://aimeos.org/integrations/typo3-shop-extension/)
* [Documentation](https://aimeos.org/docs/TYPO3)
* [Forum](https://aimeos.org/help/typo3-extension-f16/)
* [Issue tracker](https://github.com/aimeos/aimeos-typo3/issues)
* [Source code](https://github.com/aimeos/aimeos-typo3)

## Extras

Code snippets
```
php -r "readfile('https://getcomposer.org/installer');" | php -- --filename=composer
```
Another snippet
```json
{
    "name": "vendor/mysite",
    "description" : "My new TYPO3 web site",
    "minimum-stability": "dev",
    "prefer-stable": true,
    "repositories": [
        { "type": "composer", "url": "https://composer.typo3.org/" }
    ],
    "require": {
        "typo3/cms": "~8.7",
        "aimeos/aimeos-typo3": "dev-master"
    },
    "extra": {
        "typo3/cms": {
            "cms-package-dir": "{$vendor-dir}/typo3/cms",
            "web-dir": "htdocs"
        }
    },
    "scripts": {
        "post-install-cmd": [
            "Aimeos\\Aimeos\\Custom\\Composer::install"
        ],
        "post-update-cmd": [
            "Aimeos\\Aimeos\\Custom\\Composer::install"
        ]
    }
}
```

**Caution:** **Something Important. **

> **Note:** You can find more information about **LaTeX** mathematical expressions [here][4].


### UML diagrams

You can also render sequence diagrams like this:

```sequence
Alice->Bob: Hello Bob, how are you?
Note right of Bob: Bob thinks
Bob-->Alice: I am good thanks!
```

And flow charts like this:

```flow
st=>start: Start
e=>end
op=>operation: My Operation
cond=>condition: Yes or No?

st->op->cond
cond(yes)->e
cond(no)->op
```

> **Note:** You can find more information:

> - about **Sequence diagrams** syntax [here][7],
> - about **Flow charts** syntax [here][8].
