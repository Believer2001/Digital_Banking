import { Component } from '@angular/core';
import {ChartConfiguration, ChartType} from 'chart.js';
import {NgChartsModule} from 'ng2-charts';
import {DecimalPipe} from '@angular/common';

@Component({
  selector: 'app-dashbord-admin',
  imports: [
    NgChartsModule,
    DecimalPipe
  ],
  templateUrl: './dashbord-admin.component.html',
  styleUrl: './dashbord-admin.component.css'
})
export class DashbordAdminComponent {


  // 1. Nombre total de clients
  totalClients = 1200;

  // 2. Transactions du jour (nombre et montant)
  transactionsDuJourNombre = 350;
  transactionsDuJourMontant = 45000; // en €

  // 3. Répartition par type de compte
  repartitionTypesData: ChartConfiguration<'pie'>['data'] = {
    labels: ['Courant', 'Épargne', 'Entreprise', 'Jeune'],
    datasets: [{
      data: [450, 300, 250, 200],
      backgroundColor: ['#007bff', '#28a745', '#ffc107', '#dc3545']
    }]
  };
  repartitionTypesChartType: ChartType = 'pie';

  // 4. Clients actifs / inactifs (ex: derniers mois)
  clientsActifsInactifsData: ChartConfiguration<'bar'>['data'] = {
    labels: ['Actifs', 'Inactifs'],
    datasets: [
      {
        label: 'Clients',
        data: [900, 300],
        backgroundColor: ['#28a745', '#dc3545']
      }
    ]
  };
  clientsActifsInactifsChartType: ChartType = 'bar';

  // 5. Montant total en circulation (somme soldes)
  montantTotalCirculation = 7800000; // exemple en €

  // 2 bis. Transactions du jour - graphique montant sur 7 jours
  transactionsMontant7JoursData: ChartConfiguration<'bar'>['data'] = {
    labels: ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'],
    datasets: [{
      label: 'Montant transactions (€)',
      data: [5000, 7000, 6500, 8000, 6000, 9000, 7500],
      backgroundColor: '#007bff'
    }]
  };
  transactionsMontant7JoursChartType: ChartType = 'bar';



}
