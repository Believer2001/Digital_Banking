import { Component } from '@angular/core';
import { ChartConfiguration, ChartType } from 'chart.js';
import {NgChartsModule} from 'ng2-charts';
import {DecimalPipe, NgClass, NgForOf} from '@angular/common';

@Component({
  selector: 'app-dashboard',
  imports: [
    NgChartsModule,
    DecimalPipe,
    NgForOf,
    NgClass
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  // Bar chart
  public barChartData: ChartConfiguration<'bar'>['data'] = {
    labels: ['Jan', 'Feb', 'Mar', 'Apr'],
    datasets: [
      { data: [65, 59, 80, 81], label: 'Ventes' }
    ]
  };
  public barChartType: ChartType = 'bar';

  // Pie chart
  public pieChartData: ChartConfiguration<'pie'>['data'] = {
    labels: ['Produit A', 'Produit B', 'Produit C'],
    datasets: [
      { data: [300, 500, 100] }
    ]
  };
  public pieChartType: ChartType = 'pie';


 // client

   // compte
  comptes = [
    { type: 'Compte Courant', solde: 1250.75, devise: '€' },
    { type: 'Compte Épargne', solde: 5400.00, devise: '€' },
    { type: 'Compte Joint', solde: 320.50, devise: '€' }
  ];


  //historique
  // Liste des transactions récentes
  transactions = [
    { date: '2025-06-01', description: 'Paiement Supermarché', montant: -45.30 },
    { date: '2025-06-02', description: 'Virement salaire', montant: 1500.00 },
    { date: '2025-06-03', description: 'Retrait DAB', montant: -100.00 },
    { date: '2025-06-05', description: 'Achat en ligne', montant: -89.99 },
    { date: '2025-06-06', description: 'Remboursement ami', montant: 200.00 },
  ];

  // Données pour line chart : date et solde cumulatif (exemple simplifié)
  public lineChartData: ChartConfiguration<'line'>['data'] = {
    labels: ['01 Juin', '02 Juin', '03 Juin', '04 Juin', '05 Juin', '06 Juin'],
    datasets: [
      {
        data: [1200, 2700, 2600, 2600, 2510, 2710],
        label: 'Évolution du solde',
        fill: true,
        borderColor: 'blue',
        backgroundColor: 'rgba(0,0,255,0.1)',
        tension: 0.3
      }
    ]
  };
  public lineChartType: ChartType = 'line';

}
