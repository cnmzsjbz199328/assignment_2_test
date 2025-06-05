
# Race Strategy Optimiser and Car Customisation Tool

## 1. Overview

This program simulates race team management. Users can customise race cars with different engines, tyres, and aerodynamic features. The system includes a strategy optimiser to plan pit stops, fuel usage, and tyre changes based on race conditions such as track type, weather, and distance.

---

## 2. Key Features

### 2.1 Car Customisation

- Select and upgrade components: engines (e.g., standard, turbocharged), tyres (soft, medium, hard), and aerodynamic kits (see Section 4).
- Each choice affects performance metrics such as speed, fuel efficiency, and handling.

### 2.2 Race Strategy Optimisation

- Input race details (track length, weather, fuel capacity, etc.) to generate an optimal pit stop and tyre change strategy for at least 5 race tracks.
- The program should consider factors such as wear rates and fuel consumption based on selected car components.

> Some details are left open, such as acceleration, fuel tank capacity, and the effect of the cornering ability rating. You may decide these details, but must include at least 3 variations of each.

---

## 3. Testing Requirements

1. Write tests to validate car configurations (e.g., ensuring incompatible parts cannot be combined).
2. Test strategy outcomes under various race scenarios (e.g., wet vs dry weather, short vs long races).
3. Create tests for edge cases like invalid inputs (e.g., negative values for fuel or non-existent parts).
4. Simulate multiple races to test the accuracy and consistency of results based on different customisation and strategy inputs.

---

## 4. Aerodynamic Kits

Below are types of aerodynamic kits that could be incorporated into your project (select at least 4):

1. **Standard Kit (Basic Aerodynamics)**
   - *Description*: Basic front and rear spoilers to reduce drag and provide minimal downforce.
   - *Performance Impact*: Suitable for general-purpose tracks; balances speed and stability.

2. **Downforce-Focused Kit**
   - *Description*: Large front splitters and a prominent rear wing to maximise downforce.
   - *Performance Impact*: Improves cornering and traction, especially on twisty tracks, but increases drag and reduces top speed.

3. **Low-Drag Kit**
   - *Description*: Sleek, minimal features to reduce air resistance (e.g., smaller spoilers or no rear wing).
   - *Performance Impact*: Prioritises top speed on long straights but sacrifices cornering stability.

4. **Adjustable Aero Kit**
   - *Description*: Adjustable components like movable wings or variable angle splitters.
   - *Performance Impact*: Allows on-the-fly adjustments for different track sections—low downforce for straights, high downforce for corners.

5. **Ground Effect Kit**
   - *Description*: Underbody diffusers and side skirts to channel airflow underneath, creating suction.
   - *Performance Impact*: Increases downforce significantly without much drag, ideal for high-speed tracks.

6. **Drag Reduction System (DRS) Kit**
   - *Description*: Mechanism to temporarily reduce drag by altering aerodynamic components.
   - *Performance Impact*: Boosts straight-line speed when activated, aiding overtaking.

7. **Wet Weather Kit**
   - *Description*: Features like extended wheel covers or deflectors to minimise water spray and improve traction.
   - *Performance Impact*: Enhances stability and visibility in rain, sacrificing some top speed.

8. **Hybrid Kit**
   - *Description*: Combines low-drag and downforce-focused elements for a balance between speed and stability.
   - *Performance Impact*: Versatile for tracks with a mix of straights and corners.

9. **Extreme Aero Kit**
   - *Description*: Aggressive features like oversized wings, massive splitters, and large diffusers.
   - *Performance Impact*: Maximises downforce at the cost of significant drag, ideal for short, technical circuits.

---

### Table 1: Sample Parameter Values for Aerodynamic Kits

| Aerodynamic Kit         | Drag Coefficient (Cd) | Downforce (kg) | Top Speed (km/h) | Fuel Efficiency (km/l) | Cornering Ability (rating/10) |
|------------------------|----------------------|----------------|------------------|------------------------|-------------------------------|
| Standard Kit           | 0.30                 | 200            | 250              | 12                     | 6                             |
| Downforce-Focused Kit  | 0.35                 | 350            | 220              | 10                     | 9                             |
| Low-Drag Kit           | 0.25                 | 150            | 280              | 14                     | 5                             |
| Adjustable Aero Kit    | 0.28 - 0.34 (var.)   | 200 - 300 (v.) | 240 - 260 (v.)   | 11 - 13 (v.)           | 7 - 8 (v.)                    |
| Ground Effect Kit      | 0.27                 | 400            | 240              | 12                     | 8                             |
| Drag Reduction System  | 0.25 (activated)     | 200            | 290 (with DRS)   | 13                     | 6                             |
| Wet Weather Kit        | 0.32                 | 220            | 230              | 11                     | 7                             |
| Hybrid Kit             | 0.29                 | 260            | 260              | 12                     | 7                             |
| Extreme Aero Kit       | 0.40                 | 500            | 200              | 9                      | 10                            |

---

ENGR3791/ENGR9791 – Software Testing and Quality Assurance, CHM, Semester 1, May 7, 2025  
College of Science & Engineering

