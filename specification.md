# Software Testing and Quality Assurance

FLINDERS UNIVERSITY - AUSTRALIA

## Automated Code Generation and Unit Testing

Any changes or other information relating to the project will be advised via the topic Canvas page.
The purpose of this assessment is to give you an appreciation of automated code generation technologies.

### Preliminaries

1. You are to work in teams of three (3)

   i. Students can only form groups with students in the same topic (ENGR3791 or ENGR9791).

   ii. Students can form groups with students in different practical sessions.

   iii. Students will need to create their groups in the “People: Assignment 2” section on FLO and ensure all group members are registered to the group that you create.

2. You are required to use Generative AI to generate code for the case study.

   *Figure 1: Use full Gen AI for this task*

3. You are required to write unit tests for the generated code
   *(You are not permitted to use Generative AI to write the tests)*
   Failure to comply with this directive will result in a zero mark for this assignment.

   *Figure 2: No Gen AI for this task*

4. The final task is to write recommendations about the quality of the generated code.

   Further information is included in the Deliverable section

---

## Case Study

### Race Strategy Optimiser and Car Customisation Tool

#### Description

You are to build a program that acts as a race team management simulation. The system allows users to customise race cars with different engines, tyres, and aerodynamic features. It also includes a strategy optimiser to plan pit stops, fuel usage, and tyre changes based on race conditions such as track type, weather, and distance.

#### Key Features:

* **Car Customisation:**

  * Users can select and upgrade components like engines (e.g., standard, turbocharged), tyres (soft, medium, hard), and aerodynamic kits\*(see page 3).
  * Each choice affects performance metrics such as speed, fuel efficiency, and handling.

* **Race Strategy Optimisation:**

  * Users can input race details (track length, weather, fuel capacity, etc.) to generate an optimal pit stop and tyre change strategy for a minimum of 5 race tracks.
  * Your program should take into account factors such as wear rates, and fuel consumption based on the selected car components.

Some details are left open, such as acceleration, fuel tank capacity, and what effect the cornering ability rating has. In this case you are at liberty to decide these details but must include at least 3 variations of each.

---

### *Here are some types of aerodynamic kits that could be incorporated into your project (you must select at least 4):*

1. **Standard Kit (Basic Aerodynamics)**

   * *Description:* Includes basic front and rear spoilers to reduce drag and provide minimal downforce.
   * *Performance Impact:* Suitable for general-purpose tracks; balances speed and stability without extreme effects.

2. **Downforce-Focused Kit**

   * *Description:* Features large front splitters and a prominent rear wing to maximise downforce.
   * *Performance Impact:* Improves cornering ability and traction, especially on twisty tracks, but increases drag and reduces top speed.

3. **Low-Drag Kit**

   * *Description:* Designed with sleek, minimal features to reduce air resistance (e.g., smaller spoilers or absence of a rear wing).
   * *Performance Impact:* Prioritises top speed on long straight tracks but sacrifices cornering stability.

4. **Adjustable Aero Kit**

   * *Description:* Equipped with adjustable components like movable wings or variable angle splitters.
   * *Performance Impact:* Allows for on-the-fly adjustments to suit different sections of a track-low downforce for straights and high downforce for corners.

5. **Ground Effect Kit**

   * *Description:* Includes components like underbody diffusers and side skirts to channel airflow underneath the car, creating suction to stick the car to the track.
   * *Performance Impact:* Increases downforce significantly without adding much drag, making it ideal for high-speed tracks.

6. **Drag Reduction System (DRS) Kit**

   * *Description:* Features a mechanism to temporarily reduce drag by altering aerodynamic components, commonly seen in Formula 1 cars.
   * *Performance Impact:* Allows for better overtaking by boosting straight-line speed when activated.

7. **Wet Weather Kit**

   * *Description:* Designed for races in wet conditions, with features like extended wheel covers or deflectors to minimise water spray and improve traction.
   * *Performance Impact:* Enhances stability and visibility in rainy conditions, sacrificing some top speed.

8. **Hybrid Kit**

   * *Description:* Combines elements of low-drag and downforce-focused kits to achieve a middle ground between speed and stability.
   * *Performance Impact:* Offers versatility for tracks with a mix of straights and corners.

9. **Extreme Aero Kit**

   * *Description:* Pushes the limits of aerodynamics with aggressive features like oversized wings, massive splitters, and large diffusers
   * *Performance Impact:* Maximises downforce at the cost of significant drag, ideal for short, technical circuits.

| **Aerodynamic Kit**       | **Drag Coefficient (Cd)** | **Downforce (kg)**   | **Top Speed (km/h)** | **Fuel Efficiency (km/l)** | **Cornering Ability (rating out of 10)** |
| ------------------------- | ------------------------- | -------------------- | -------------------- | -------------------------- | ---------------------------------------- |
| Standard Kit              | 0.30                      | 200                  | 250                  | 12                         | 6                                        |
| Downforce-Focussed Kit    | 0.35                      | 350                  | 220                  | 10                         | 9                                        |
| Low-Drag Kit              | 0.25                      | 150                  | 280                  | 14                         | 5                                        |
| Adjustable Aero Kit       | 0.28 - 0.34 (variable)    | 200 - 300 (variable) | 240 - 260 (variable) | 11 - 13 (variable)         | 7 - 8 (variable)                         |
| Ground Effect Kit         | 0.27                      | 400                  | 240                  | 12                         | 8                                        |
| Drag Reduction System Kit | 0.25 (activated)          | 200                  | 290 (with DRS)       | 13                         | 6                                        |
| Wet Weather Kit           | 0.32                      | 220                  | 230                  | 11                         | 7                                        |
| Hybrid Kit                | 0.29                      | 260                  | 260                  | 12                         | 7                                        |
| Extreme Aero Kit          | 0.4                       | 500                  | 200                  | 9                          | 10                                       |

---

## Deliverables

You are required to submit a report consisting of three sections as follows:

 **List of Prompts used to generate the code – A minimum of 10 iterations of prompts (15 marks):**
   Only the prompts are required.
   Prompt engineering is a technique of designing inputs for the tools like ChatGPT. The input could be a set of requirements (instructions) that will produce the required code. The initial output (code) of ChatGPT can be improved by providing more precise prompts (requirements) to ChatGPT.

   For the final report you are required to do the following:

   i. Document all the prompts that you used to generate the code

   *See Appendix 1 for an example of code generation using prompts.*